package ru.alina.repository;

import ru.alina.model.Summary;

import java.util.List;

public interface SummaryRepository {
    Summary save(Summary summary, int userId);

    // false if summary do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Summary get(int id, int userId);

    // ORDERED dateTime desc
    List<Summary> getAll(int userId);

    long countChecked(int userId);

/*   long countAllQuestion(int userId); */


    List<Summary> getByTopic(int topicId, int userId);

    //null if empty
    Summary getRandomNotChecked(int userId);

    //empty list if list empty
    List<Summary> getCheckedSummary(int userId);

    long getCount(int userId);

    long getCountCheckedByTopic(int topicId, int userId);

    long getCountUncheckedByTopic(int topicId, int userId);


}
