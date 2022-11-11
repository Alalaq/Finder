package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.University;
import ru.kpfu.itis.entities.User;

import java.util.List;

public interface UserUniversityRepository {
    void add(University university, User user);

    List<University> getAll();
}
