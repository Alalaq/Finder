package ru.kpfu.itis.repositories.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.repositories.ProfilesRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public class ProfilesRepositoryJdbcTemplateImpl implements ProfilesRepository {
    //language=SQL
    private static final String SQL_SELECT_ALL_PROFILES = "select * from profiles;";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from profiles where id = ?;";

    //language=SQL
    private static final String SQL_INSERT_EMPTY_PROFILE = "insert into profiles(id) values (?);";

    //language=SQL
    private static final String SQL_INSERT_PROFILE = "insert into profiles(id, name, city, sex, age, description, hobbies, min_prefered_age, max_prefered_age, sex_prefered, connection_with_user)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE_PROFILE_BY_ID = "update profiles set name = ?, city = ?, sex = ?, age = ?," +
            " description = ?, hobbies = ?, min_prefered_age = ?, max_prefered_age = ?, sex_prefered = ?, connection_with_user = ? where id = ?;";

    //language=SQL
    private static final String SQL_SELECT_MAX_ID = "select id from profiles order by id desc limit 1;";

    private static final RowMapper<Profile> ProfileMapper = (row, rowNumber) -> Profile.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .city(row.getString("city"))
            .sex(row.getString("sex"))
            .age(row.getObject("age", Integer.TYPE))
            .description(row.getString("description"))
            .hobbies(Collections.singletonList(row.getString("hobbies")))
            .minAgePrefered(row.getInt("min_age_prefered"))
            .maxAgePrefered(row.getInt("max_age_prefered"))
            .sexPrefered(row.getString("sex_prefered"))
            .connect(row.getString("connection_with_user"))
            .build();

    private final DataSource dataSource;

    public ProfilesRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addEmptyProfile(Long id){
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_EMPTY_PROFILE)) {
            statement.setLong(1, id);

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save profile");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Profile profile) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PROFILE)) {

            statement.setObject(1, profile.getId());
            statement.setString(2, profile.getName());
            statement.setString(3, profile.getCity());
            statement.setString(4, profile.getSex());
            statement.setObject(5, profile.getAge());
            statement.setString(6, profile.getDescription());
            statement.setObject(7, profile.getHobbies());
            statement.setInt(8, profile.getMinAgePrefered());
            statement.setInt(9, profile.getMaxAgePrefered());
            statement.setString(10, profile.getSexPrefered());
            statement.setString(11, profile.getConnect());

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save profile");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public List<Profile> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        return jdbcTemplate.query(SQL_SELECT_ALL_PROFILES, ProfileMapper);
    }

    @Override
    public void update(Profile profile) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PROFILE_BY_ID)) {

            Array list = null;
            if (profile.getHobbies() != null) {
                list = connection.createArrayOf("varchar", profile.getHobbies().toArray());
            }

            statement.setString(1, profile.getName());
            statement.setString(2, profile.getCity());
            statement.setString(3, profile.getSex());
            statement.setInt(4, profile.getAge());
            statement.setString(5, profile.getDescription());
            statement.setArray(6, list);
            statement.setInt(7, profile.getMinAgePrefered());
            statement.setInt(8, profile.getMaxAgePrefered());
            statement.setString(9, profile.getSexPrefered());
            statement.setString(10, profile.getConnect());
            statement.setLong(11, profile.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't update profile");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Profile getProfileById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Profile profile = new Profile();

            if (resultSet.next()) {
                profile.setId(resultSet.getLong(1));

                profile.setName(resultSet.getString("name"));

                profile.setCity(resultSet.getString("City"));

                profile.setSex(resultSet.getString("sex"));

                profile.setAge(resultSet.getInt("age"));

                profile.setDescription(resultSet.getString("description"));

                profile.setHobbies(getListFromArray(resultSet, "hobbies"));

                profile.setMinAgePrefered(resultSet.getInt("min_prefered_age"));

                profile.setMaxAgePrefered(resultSet.getInt("max_prefered_age"));

                profile.setSexPrefered(resultSet.getString("sex_prefered"));

                profile.setConnect(resultSet.getString("connection_with_user"));
            }

            return profile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getMaxId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        return jdbcTemplate.queryForObject(SQL_SELECT_MAX_ID, Long.class);
    }

    public static List<String> getListFromArray(ResultSet rs, String columnLabel) {
        List<String> al = new ArrayList<>();
        Array z;
        try {
            z = rs.getArray(columnLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (z == null){
            return al;
        }
        try {
            for (Object obj : (Object[]) z.getArray()) {
                try {
                    String arr = (String) obj;
                    al.add(arr);
                } catch (ClassCastException e) {
                    System.out.println("Object is not a String[]");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return al;
    }
}
