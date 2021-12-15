package ru.alina;

import ru.alina.model.Topic;
import ru.alina.model.TopicSelected;
import ru.alina.model.User;

import java.util.List;

public class TopicData {
    public static final int TOPIC1_ID = 3;
    public static final int TOPIC2_ID = 4;
    public static final int TOPI_SELETED_ID = 5;
    public final static int USER_ID = 1;
    public final static int USER_EMPTY_ID = 2;
    public final static int NOT_FOUND_ID = 1000;

    public static final Topic TOPIC1 = new Topic(TOPIC1_ID,  "ООП");
    public static final Topic TOPIC2 = new Topic(TOPIC2_ID,  "SQL");
    public static final Topic TOPIC2_WITH_USER = new Topic(TOPIC2_ID,  "SQL", new User(USER_ID, "l", "p"));
    public static final Topic TOPIC_SELECTED = TOPIC1;

    public static final List<Topic> TOPICS = List.of(TOPIC2, TOPIC1);
    public static final List<Topic> TOPICS_NOT_EMPTY_SUMMARIES = List.of(TOPIC1);

    public static Topic getNew() {
        return new Topic(null, "new");
    }

    public static Topic getUpdated() {
        return new Topic(TOPIC1_ID, "updated");
    }
}
