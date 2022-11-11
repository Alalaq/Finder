package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.DuplicateEntryException;
import ru.kpfu.itis.exceptions.WrongDateException;
import ru.kpfu.itis.exceptions.WrongEmailException;

import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public interface UsersRepository {
    long add(User user)  throws DuplicateEntryException, WrongEmailException;

    List<User> getAll();

    User getUserByEmail(User user);

    void update(User User);

    void updateLastViewed(Long users_id, Long new_last_viewed);
}
