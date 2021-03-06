package ru.alina.repository;

import ru.alina.model.Topic;
import ru.alina.model.TopicSelected;

public interface TopicSelectedRepository {
    Topic getTopicSelected(int userId);

    int getId(int userId);

    void update(Topic topic, int userId);

    TopicSelected get(int userId);

    TopicSelected create(TopicSelected topicSelected, int userId);
}
