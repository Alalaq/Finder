package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.DuplicateEntryException;
import ru.kpfu.itis.exceptions.WrongEmailException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public interface ProfilesRepository {
    void addEmptyProfile(Long id);

    void add(Profile profile);

    List<Profile> getAll();

    void update(Profile profile);

    Profile getProfileById(Long id);

    Long getMaxId();
}
