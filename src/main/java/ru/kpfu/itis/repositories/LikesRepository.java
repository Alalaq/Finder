package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Like;
import ru.kpfu.itis.entities.Profile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public interface LikesRepository {
    void add(Like like);

    List<Like> getAll();

    void update(Like like);

    List<Long> getAllLikesOfUser(long id);

    boolean checkIfTheresLike(Long idOfLikedUser, Long idOfUserWhoLike);

    void deleteLike(Like like);
}
