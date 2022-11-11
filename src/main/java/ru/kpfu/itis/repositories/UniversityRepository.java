package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Like;
import ru.kpfu.itis.entities.University;

import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

public interface UniversityRepository {
    void add(University university);

    List<University> getAll();

    University getByTitle(String title);

    void update(University university);
}
