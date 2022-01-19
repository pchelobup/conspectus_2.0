package ru.alina.repository;

import ru.alina.model.Summary;
import ru.alina.model.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    User update(User user);

    boolean delete(int id);

    User get(int id);

    List<User> getAll();
}
