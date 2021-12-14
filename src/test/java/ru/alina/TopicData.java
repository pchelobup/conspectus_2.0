package ru.alina;

import ru.alina.model.Topic;
import ru.alina.model.User;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicData {
    public static final int TOPIC1_ID = 3;
    public static final int TOPIC2_ID = 4;
    public static final int TOPIC3_ID = 5;
    public static final int TOPIC_SELETED_ID = 5;
    public final static int USER_ID = 1;

    public final static int USER_EMPTY_ID = 2;
    public final static int NOT_FOUND_ID = 1000;

    public static final Topic TOPIC1 = new Topic(TOPIC1_ID,  "ООП");
    public static final Topic TOPIC2 = new Topic(TOPIC2_ID,  "SQL");
    public static final Topic TOPIC3 = new Topic(TOPIC3_ID,  "spring");
    public static final Topic TOPIC2_WITH_USER = new Topic(TOPIC2_ID,  "SQL", new User(USER_ID, "l", "p"));
    public static final Topic TOPIC_SELECTED = TOPIC1;

    public static final List<Topic> TOPICS = List.of(TOPIC2, TOPIC3, TOPIC1);
    public static final List<Topic> TOPICS_NOT_EMPTY_SUMMARIES = List.of(TOPIC2, TOPIC1);

    public static Topic getNew() {
        return new Topic(null, "new");
    }

    public static Topic getUpdated() {
        return new Topic(TOPIC1_ID, "updated");
    }

    public static void match(Topic actual, Topic expeted) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("summaries", "user")
                .isEqualTo(expeted);
    }

    public static void match(List<Topic> actual, List<Topic> expeted) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("summaries", "user")
                .isEqualTo(expeted);
    }
}
