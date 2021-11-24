package ru.alina.repository;

import ru.alina.model.Topic;

import java.util.List;

public interface TopicRepository {
    Topic save(Topic topic, int userId);

    // false if summary do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Topic get(int id, int userId);

    // ORDERED name return empty if empty
    List<Topic> getAll(int userId);

  //  Topic getByName(String name, int userId);
}
