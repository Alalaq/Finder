package ru.kpfu.itis.repositories.impl;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.encryption.PasswordEncryptor;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.DuplicateEntryException;
import ru.kpfu.itis.exceptions.WrongEmailException;
import ru.kpfu.itis.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL_USERS = "select * from users;";

    //language=SQL
    private static final String SQL_INSERT_USER = "insert into users(email, password) " +
            "values (?, ?)";


    //language=SQL
    private static final String SQL_UPDATE_USER_BY_ID = "update users set email = ?, password = ?, last_viewed_profile_id = ?" +
            " where id = ?;";

    //language=SQL
    private static final String SQL_UPDATE_USERS_LAST_VIEWED_BY_ID = "update users set last_viewed_profile_id = ?" +
            " where id = ?;";

    //language=SQL
    private static final String SQL_GET_USER_BY_EMAIL = "select * from users where email = ?;";

    private static final RowMapper<User> UserMapper = (row, rowNumber) -> User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .password(row.getString("password"))
            .lastViewedUser(row.getLong("last_viewed_profile_id"))
            .build();


    private final DataSource dataSource;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, UserMapper);
    }

    @Override
    public User getUserByEmail(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        return jdbcTemplate.queryForObject(SQL_GET_USER_BY_EMAIL, UserMapper, user.getEmail());
    }

    @SneakyThrows
    @Override
    public long add(User user) throws DuplicateEntryException, WrongEmailException {

        String regex = "([A-Za-z0-9]{1,}[\\.-]{0,1}[A-Za-z0-9]{1,})+@([A-Za-z0-9]{1,}[\\.-]{0,1}[A-Za-z0-9]{1,})+[\\.]{1}[a-z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            throw new WrongEmailException();
        }

        List<User> users = getAll();

        String password = PasswordEncryptor.generateStrongPasswordHash(user.getPassword());

        if (users.size() != 0) {
            for (User user1 : users) {
                if (user1.getEmail().equals(user.getEmail())) {
                    throw new DuplicateEntryException();
                }
            }
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, password);

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save user");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                long id = generatedKeys.getLong("id");
                user.setId(id);
                return id;
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(User User) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID)) {

            statement.setString(1, User.getEmail());
            statement.setString(2, User.getPassword());
            statement.setLong(3, User.getLastViewedUser());
            statement.setLong(4, User.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't update user");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void updateLastViewed(Long users_id, Long new_last_viewed){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USERS_LAST_VIEWED_BY_ID)) {

            statement.setLong(1, new_last_viewed);
            statement.setLong(2, users_id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't update user");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

