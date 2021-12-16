package ru.alina.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alina.SummaryData.*;

class SummaryServiceTest extends ServiceTest {

    private SummaryService summaryService;

    @Autowired
    public void setSummaryService(SummaryService summaryService) {
        this.summaryService = summaryService;
    }
    @Test
    void get() {
        Summary actual = summaryService.get(SUMMARY1_ID, USER_ID);
        match(actual, SUMMARY1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> summaryService.get(NOT_FOUND_ID, USER_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class,
                () -> summaryService.get(SUMMARY1_ID, USER_EMPTY_ID));
    }

    @Test
    void getRandomNotChecked() {
        Summary actual = summaryService.getRandomNotChecked(USER_ID);
        assertThat(SUMMARIES_NOT_CHECKED)
                .contains(actual);

    }

    @Test
    void getAll() {
        List<Summary> actual = summaryService.getAll(USER_ID);
        match(actual, SUMMARIES);
    }

    @Test
    void getCheckedSummary() {
        List<Summary> actual = summaryService.getCheckedSummary(USER_ID);
        match(actual, SUMMARIES_CHECKED);
    }

    @Test
    void getByTopic() {
        List<Summary> actual = summaryService.getByTopic(TOPIC_ID, USER_ID);
        match(actual, SUMMARIES_ONE_TOPIC);
    }

    @Test
    void getByTopicNotOwn() {
        List<Summary> actual = summaryService.getByTopic(TOPIC_ID, USER_EMPTY_ID);
        assertThat(actual).isNull();
    }

    @Test
    void getByTopicNotFound() {
        List<Summary> actual = summaryService.getByTopic(NOT_FOUND_ID, USER_ID);
        assertThat(actual).isNull();
    }

    @Test
    void create() {
        Summary created = summaryService.create(getNew(), USER_ID);
        int newId = created.getId();
        Summary summary = getNew();
        summary.setId(newId);
        match(created, summary);
        match(summaryService.get(newId, USER_ID), summary);
    }

    @Test
    void update() {
        Summary updated = getUpdated();
        summaryService.update(updated, USER_ID);
        match(summaryService.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    void updateNotOwn() {
        Summary updated = getUpdated();
        assertThrows(NotFoundException.class,
                () -> summaryService.update(updated, USER_EMPTY_ID));

        Summary actual = summaryService.get(SUMMARY1_ID, USER_ID);
        match(actual, SUMMARY1);
    }

    @Test
    void delete() {
        summaryService.delete(SUMMARY1_ID, USER_ID);
        assertThrows(NotFoundException.class,
                () -> summaryService.get(SUMMARY1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class,
                () -> summaryService.delete(NOT_FOUND_ID, USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class,
                () -> summaryService.delete(SUMMARY1_ID, USER_EMPTY_ID));
    }


    @Test
    void countChecked() {
        assertThat(summaryService.countChecked(USER_ID))
                .isEqualTo(SUMMARIES_CHECKED.size());
    }

    @Test
    void getCount() {
        assertThat(summaryService.getCount(USER_ID))
                .isEqualTo(SUMMARIES.size());
    }

    @Test
    void getCountCheckedByTopic() {
        assertThat(summaryService.getCountCheckedByTopic(TOPIC_ID, USER_ID))
                .isEqualTo(COUNT_CHECKED_BY_TOPIC);
    }

    @Test
    void getCountUncheckedByTopic() {
        assertThat(summaryService.getCountCheckedByTopic(TOPIC_ID, USER_ID))
                .isEqualTo(COUNT_NOT_CHECKED_BY_TOPIC);
    }
}