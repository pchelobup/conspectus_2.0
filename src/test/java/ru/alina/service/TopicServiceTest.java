package ru.alina.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.model.Topic;
import ru.alina.util.exception.NotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alina.TopicData.*;


class TopicServiceTest extends ServiceTest {

    @Autowired
    private TopicService topicService;

    @Test
    void delete() {
        topicService.delete(TOPIC1_ID, USER_ID);
        assertThrows(NotFoundException.class,
                () -> topicService.get(TOPIC1_ID, TOPIC1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> topicService.delete(NOT_FOUND_ID, USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> topicService.delete(TOPIC1_ID, USER_EMPTY_ID));
    }

    @Test
    void get() {
        Topic actual = topicService.get(TOPIC1_ID, USER_ID);
        match(actual, TOPIC1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> topicService.get(NOT_FOUND_ID, USER_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> topicService.get(TOPIC1_ID, USER_EMPTY_ID));
    }

    @Test
    void getAll() {
        List<Topic> actual = topicService.getAll(USER_ID);
        match(actual, TOPICS);
    }

    @Test
    void getTopicWithNotEmptySummary() {
        List<Topic> actual = topicService.getTopicWithNotEmptySummary(USER_ID);
        match(actual, TOPICS_NOT_EMPTY_SUMMARIES);
    }

    @Test
    void getTopicSelected() {
        Topic actual = topicService.getTopicSelected(USER_ID);
        match(actual, TOPIC_SELECTED);
    }

    @Test
    void create() {
        Topic created = topicService.create(getNew(), USER_ID);
        int newId = created.getId();
        Topic topic = getNew();
        topic.setId(newId);
        match(created, topic);
        match(topicService.get(newId, USER_ID), topic);
    }

    @Test
    void dublicateTopicName() {
        assertThrows(PersistenceException.class,
                () ->topicService
                        .create(new Topic(null, TOPIC1.getName()), USER_ID));
    }

    @Test
    void update() {
        Topic updated = getUpdated();
        topicService.update(updated, USER_ID);
        match(topicService.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    void updateNotOwn() {
        Topic updated = getUpdated();
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> topicService.update(updated, USER_EMPTY_ID));

        assertEquals("Not found entity " + Topic.class.getSimpleName() +" with id="+TOPIC1_ID, exception.getMessage());

        Topic actual = topicService.get(TOPIC1_ID, USER_ID);
        match(actual, TOPIC1);
    }

    @Test
    void updateTopicSelected() {
        topicService.saveUpdateTopicSelected(TOPIC2_WITH_USER, USER_ID);
        Topic actual = topicService.getTopicSelected(USER_ID);
        match(actual, TOPIC2);
    }
    @Test
    void updateTopicSelectedNotOwn() {
        Topic updated = topicService.getTopicSelected(USER_ID);
        assertThrows(NotFoundException.class,
                () -> topicService.saveUpdateTopicSelected(updated, USER_EMPTY_ID));

        match(updated, TOPIC_SELECTED);
    }

    @Test
    void updateTopicSelectedEmpty() {
        assertThrows(NullPointerException.class,
                () -> topicService.saveUpdateTopicSelected(null, USER_ID));
    }

    @Test
    void createTopicSelected() {
        Topic created = topicService.create(getNew(), USER_EMPTY_ID);
        int newId = created.getId();
        Topic topic = getNew();
        topic.setId(newId);
        Topic topicSelected = topicService.getTopicSelected(USER_EMPTY_ID);
        match(topicSelected, topic);
    }


}