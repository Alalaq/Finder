package ru.kpfu.itis.repositories.impl;

import ru.kpfu.itis.entities.University;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.repositories.UserUniversityRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserUniversityRepositoryJdbcTemplateImpl implements UserUniversityRepository {
    //language = sql
    private static final String SQL_INSERT_USER_UNIVERSITY = "insert into user_university(users_id, university_id) " +
            "values (?, ?)";

    private final DataSource dataSource;

    public UserUniversityRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(University university, User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_UNIVERSITY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(2, university.getId());
            statement.setLong(1, user.getId());

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save users university");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<University> getAll() {
        return null;
    }
}
