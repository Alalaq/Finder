package ru.kpfu.itis.repositories;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;

import java.util.List;

public interface MutualsRepository {
    void add(Profile firstUser, Profile secondUser);

    List<Long> getAllMutualsOfUser(User user);
}
