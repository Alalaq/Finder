package ru.kpfu.itis.repositories.impl;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.repositories.MutualsRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MutualsRepositoryJdbcTemplateImpl implements MutualsRepository {

    //language=SQL
    private static final String SQL_INSERT_MUTUAL = "insert into mutuals(id_of_first_user, id_of_second_user) VALUES " +
            "(?, ?);";

    //language=SQL
    private static final String SQL_SELECT_ALL_MUTUALS_OF_USER = "select * from mutuals where id_of_first_user = ? or id_of_second_user = ?;";

    private final DataSource dataSource;

    public MutualsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Profile firstUser, Profile secondUser) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MUTUAL)){

            statement.setLong(1, firstUser.getId());
            statement.setLong(2, secondUser.getId());

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save mutual");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getAllMutualsOfUser(User user) {
        List<Long> mutuals = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_MUTUALS_OF_USER)) {
            statement.setLong(1, user.getId());
            statement.setLong(2, user.getId());

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                if (rs.getLong("id_of_first_user") == user.getId()){
                    mutuals.add(rs.getLong("id_of_second_user"));
                }
                else if (rs.getLong("id_of_second_user") == user.getId()){
                    mutuals.add(rs.getLong("id_of_first_user"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (mutuals.size() == 0){
            return null;
        }
        return mutuals;
    }
}
