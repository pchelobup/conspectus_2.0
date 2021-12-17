package ru.alina.repository;

import ru.alina.model.Summary;

import java.util.List;

public interface SummaryRepository {
    Summary save(Summary summary, int topicId, int userId);

    // false if summary do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Summary get(int id, int userId);

    Summary getWithTopic(int id, int userId);

    // ORDERED dateTime desc
    List<Summary> getAll(int userId);

    long countChecked(int userId);

/*   long countAllQuestion(int userId); */


    List<Summary> getByTopic(int topicId, int userId);

    List<Summary> getByTopicName(String topicName, int userId);

    //null if empty
    Summary getRandomNotCheckedWithTopic(int userId);

    //empty list if list empty
    List<Summary> getCheckedSummaryWithTopic(int userId);

    long getCount(int userId);

    long getCountCheckedByTopic(int topicId, int userId);

    long getCountUncheckedByTopic(int topicId, int userId);


}
