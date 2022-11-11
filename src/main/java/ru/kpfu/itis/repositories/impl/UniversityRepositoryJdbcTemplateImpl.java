package ru.kpfu.itis.repositories.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.entities.University;
import ru.kpfu.itis.repositories.UniversityRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public class UniversityRepositoryJdbcTemplateImpl implements UniversityRepository {
    //language=SQL
    private static final String SQL_SELECT_ALL_UNIVERSITIES = "select * from university;";

    //language=SQL
    private static final String SQL_SELECT_UNIVERSITY_BY_TITLE = "select * from university where title = ?;";

    //language=SQL
    private static final String SQL_INSERT_UNIVERSITY= "insert into university(title, city) " +
            "values (?, ?)";

    //language=SQL
    private static final String SQL_UPDATE_UNIVERSITY_BY_ID = "update university set title = ?, city = ? where id = ?;";

    private static final RowMapper<University> UniversityMapper = (row, rowNumber) -> University.builder()
            .id(row.getLong("id"))
            .title(row.getString("title"))
            .city(row.getString("city"))
            .build();


    private final DataSource dataSource;

    public UniversityRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(University university) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_UNIVERSITY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, university.getTitle());
            statement.setString(2, university.getCity());

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save university");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                long id = generatedKeys.getLong("id");
                university.setId(id);
            } else {
                throw new SQLException("Can't obtain generated key");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public List<University> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        return jdbcTemplate.query(SQL_SELECT_ALL_UNIVERSITIES, UniversityMapper);
    }

    @Override
    public University getByTitle(String title) {
        University university = new University();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_UNIVERSITY_BY_TITLE)) {

            statement.setString(1, title);

            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                university.setId(rs.getLong(1));
                university.setTitle(title);
                university.setCity(rs.getString("city"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return university;
    }

    @Override
    public void update(University university) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_UNIVERSITY_BY_ID)) {

            statement.setString(1, university.getTitle());
            statement.setString(2, university.getCity());
            statement.setLong(3, university.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't update university");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
