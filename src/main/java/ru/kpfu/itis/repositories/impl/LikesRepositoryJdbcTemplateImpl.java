package ru.kpfu.itis.repositories.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.entities.Like;
import ru.kpfu.itis.repositories.LikesRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public class LikesRepositoryJdbcTemplateImpl implements LikesRepository {
    //language=SQL
    private static final String SQL_SELECT_ALL_LIKES = "select * from likes;";

    //language=SQL
    private static final String SQL_SELECT_ALL_LIKES_OF_USER = "select * from likes where id = ";

    //language=SQL
    private static final String SQL_INSERT_LIKE = "insert into likes(id, likes_from) " +
            "values (?, ?)";

    //language=SQL
    private static final String SQL_FIND_LIKE_OF_USER = "select * from likes where id = ? and likes_from = ?;";

    //language=SQL
    private static final String SQL_DELETE_LIKE_BY_IDS = "delete from likes where id = ? and likes_from = ?;";

    private static final RowMapper<Like> LikeMapper = (row, rowNumber) -> Like.builder()
            .idOfUser(row.getLong("id"))
            .idOfUserWhoLiked(row.getLong("like_from"))
            .build();

    private final DataSource dataSource;

    public LikesRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Like like) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LIKE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, like.getIdOfUser());
            statement.setObject(2, like.getIdOfUserWhoLiked());

            boolean affectedRows = statement.execute();

            if (affectedRows) {
                throw new SQLException("Can't save like");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Like> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        return jdbcTemplate.query(SQL_SELECT_ALL_LIKES, LikeMapper);
    }

    @Override
    public void update(Like like) {

    }

    @Override
    public List<Long> getAllLikesOfUser(long id){
        String sql_req = SQL_SELECT_ALL_LIKES_OF_USER + id + ";";
        ArrayList<Long> likes = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()){
            try (ResultSet rs = statement.executeQuery(sql_req)) {
                for (int i = 1; rs.next(); i++){
                    likes.add(rs.getLong(i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public boolean checkIfTheresLike(Long idOfLikedUser, Long idOfUserWhoLike){
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_LIKE_OF_USER)){

            statement.setLong(1, idOfLikedUser);
            statement.setLong(2, idOfUserWhoLike);

            ResultSet rs = statement.executeQuery();

            return rs.next();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteLike(Like like){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_LIKE_BY_IDS)){

            statement.setLong(1, like.getIdOfUser());
            statement.setLong(2, like.getIdOfUserWhoLiked());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0){
                throw new SQLException("Couldn't delete like");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
