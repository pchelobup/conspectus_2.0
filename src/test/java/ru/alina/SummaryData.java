package ru.alina;

import org.junit.jupiter.api.Assertions;
import ru.alina.model.Summary;
import ru.alina.model.Topic;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.alina.TopicData.TOPIC1_ID;

public class SummaryData {
    public static final int SUMMARY1_ID = 6;
    public static final int SUMMARY2_ID = 7;
    public static final int SUMMARY3_ID = 8;
    public static final int SUMMARY4_ID = 9;
    public static final int SUMMARY5_ID = 10;
    public final static int NOT_FOUND_ID = 1000;
    public final static int USER_ID = 1;
    public final static int USER_EMPTY_ID = 2;
    public static final int TOPIC_ID = 3;
    public final static int COUNT_CHECKED_BY_TOPIC = 2;
    public final static int COUNT_NOT_CHECKED_BY_TOPIC = 2;

    public static final Summary SUMMARY1 = new Summary(SUMMARY1_ID, "Что такое ООП", "ООП - программа предcтавленна в виде совокупноти объектов", true);
    public static final Summary SUMMARY2 = new Summary(SUMMARY2_ID, "Принципы ООП","наследование, инкапсуляция, абстракция, полиморфизм", false);
    public static final Summary SUMMARY3 = new Summary(SUMMARY3_ID, "Объект","сущность реального мира, которая является основной единицей ООП", true);
    public static final Summary SUMMARY4 = new Summary(SUMMARY4_ID, "Класс","шаблон для объета", false);
    public static final Summary SUMMARY5 = new Summary(SUMMARY5_ID, "UNIQUE","гарантирует уникальность значений в столбце", true);

    public static final List<Summary> SUMMARIES = List.of(SUMMARY1, SUMMARY2, SUMMARY3, SUMMARY4, SUMMARY5);
    public static final List<Summary> SUMMARIES_CHECKED = List.of(SUMMARY1, SUMMARY3, SUMMARY5);
    public static final List<Summary> SUMMARIES_NOT_CHECKED = List.of(SUMMARY2, SUMMARY4);
    public static final List<Summary> SUMMARIES_ONE_TOPIC = List.of(SUMMARY1, SUMMARY2, SUMMARY3, SUMMARY4);

    public static Summary getNew() {
        return new Summary(null, "new question", "new answer", false, new Topic(TOPIC1_ID,  "ООП"));
    }

    public static Summary getUpdated() {
        return new Summary(SUMMARY1_ID, "updated questuin", "updated answer", SUMMARY1.isCheck(), new Topic(TOPIC1_ID,  "ООП"));
    }

    public static void match(Summary actual, Summary expeted) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("topic", "user")
                .isEqualTo(expeted);
    }

    public static void match(List<Summary> actual, List<Summary> expeted) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("topic", "user")
                .isEqualTo(expeted);
    }
}
