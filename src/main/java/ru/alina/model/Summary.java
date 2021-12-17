package ru.alina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = Summary.DELETE, query = "DELETE from Summary s where s.id=:id and s.user.id=:userId"),
        @NamedQuery(name = Summary.ALL, query = "SELECT s from Summary s where s.user.id=:userId"),
        @NamedQuery(name = Summary.COUNT_CHECKED, query = "SELECT COUNT(s) from Summary s where s.user.id=:userId and s.check=true"),
        @NamedQuery(name = Summary.ALL_QUESTION_COUNT, query = "SELECT COUNT(s) from Summary s where s.user.id=:userId"),
        @NamedQuery(name = Summary.BY_TOPIC_ID, query = "SELECT s from Summary s where s.user.id=:userId and s.topic.id=:topicId"),
        @NamedQuery(name = Summary.BY_TOPIC_NAME, query = "SELECT s from Summary s where s.user.id=?1 and s.topic.name=?2"),
        @NamedQuery(name = Summary.CHECKED_SUMMARY_WITH_TOPIC, query = "SELECT s from Summary s join fetch s.topic WHERE s.user.id=:userId and s.check=true"),
        @NamedQuery(name = Summary.COUNT, query = "SELECT COUNT(s) from Summary s WHERE s.user.id=:userId"),
        @NamedQuery(name = Summary.COUNT_CHECKED_BY_TOPIC, query = "SELECT COUNT(s) from Summary s WHERE s.topic.id=:topicId and s.user.id=:userId and s.check=true"),
        @NamedQuery(name = Summary.COUNT_UNCHECKED_BY_TOPIC, query = "SELECT COUNT (s) from Summary s WHERE s.topic.id=:topicId and s.user.id=:userId and s.check=false"),
        @NamedQuery(name = Summary.GET_WITH_TOPIC, query = "SELECT s from Summary s JOIN FETCH s.topic t where s.id=:id and s.user.id=:userId ")
}
)
@Entity
@Table(name = "summary")
public class Summary extends BaseEntity {

    public static final String DELETE = "Summary.delete";
    public static final String ALL = "Summary.getAllSorted";
    public static final String COUNT_CHECKED = "Summary.checkedCount";
    public static final String ALL_QUESTION_COUNT = "Summary.allQuestionCount";
    public static final String BY_TOPIC_ID = "Summary.byTopicId";
    public static final String BY_TOPIC_NAME = "Summary.byTopicName";
    public static final String CHECKED_SUMMARY_WITH_TOPIC = "Summary.getCheckedSummaryWithTopic";
    public static final String COUNT = "Summary.getCount";
    public static final String COUNT_CHECKED_BY_TOPIC = "Summary.getCountCheckedByTopic";
    public static final String COUNT_UNCHECKED_BY_TOPIC = "Summary.getCountUncheckedByTopic";
    public static final String GET_WITH_TOPIC = "Summary.getWithTopic";

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    @Column(name = "checked", nullable = false)
    private boolean check;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
   // @JsonIgnore
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Summary(Integer id, String question, String answer, boolean check, Topic topic) {
        super(id);
        this.question = question;
        this.answer = answer;
        this.check = check;
        this.topic = topic;
    }

    public Summary(String question, String answer, boolean check, Topic topic) {
        this(null, question, answer, check, topic);
    }

    /* for test */

    public Summary(Integer id, String question, String answer, boolean check) {
        super(id);
        this.question = question;
        this.answer = answer;
        this.check = check;
    }

    public Summary() {
    }

    public Summary(String question, String answer, boolean check) {
        this.question = question;
        this.answer = answer;
        this.check = check;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", topic=" + topic.getId() +
                ", check=" + check +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary summary = (Summary) o;
        return check == summary.check &&
                question.equals(summary.question) &&
                answer.equals(summary.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
