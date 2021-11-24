package ru.alina.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = Summary.DELETE, query = "DELETE from Summary s where s.id=:id and s.user.id=:userId"),
        @NamedQuery(name = Summary.ALL, query = "SELECT s from Summary s where s.user.id=:userId"),
        @NamedQuery(name = Summary.COUNT_CHECKED, query = "SELECT COUNT(s) from Summary s where s.user.id=:userId and s.check=true"),
        @NamedQuery(name = Summary.ALL_QUESTION_COUNT, query = "SELECT COUNT(s) from Summary s where s.user.id=:userId"),
        @NamedQuery(name = Summary.BELONG_TOPIC, query = "SELECT s from Summary s where s.user.id=:userId and s.topic.id=:topicId"),
        @NamedQuery(name = Summary.CHECKED_SUMMARY, query = "SELECT s from Summary s WHERE s.user.id=:userId and s.check=true"),
        @NamedQuery(name = Summary.COUNT, query = "SELECT COUNT(s) from Summary s WHERE s.user.id=:userId"),
        @NamedQuery(name = Summary.COUNT_CHECKED_BY_TOPIC, query = "SELECT COUNT(s) from Summary s WHERE s.topic.id=:topicId and s.user.id=:userId and s.check=true"),
        @NamedQuery(name = Summary.COUNT_UNCHECKED_BY_TOPIC, query = "SELECT COUNT (s) from Summary s WHERE s.topic.id=:topicId and s.user.id=:userId and s.check=false")
}
)
@Entity
@Table(name = "summary")
public class Summary extends BaseEntity {

    public static final String DELETE = "Summary.delete";
    public static final String ALL = "Summary.getAllSorted";
    public static final String COUNT_CHECKED = "Summary.checkedCount";
    public static final String ALL_QUESTION_COUNT = "Summary.allQuestionCount";
    public static final String BELONG_TOPIC = "Summary.belongTopic";
    public static final String CHECKED_SUMMARY = "Summary.getCheckedSummary";
    public static final String COUNT = "Summary.getCount";
    public static final String COUNT_CHECKED_BY_TOPIC = "Summary.getCountCheckedByTopic";
    public static final String COUNT_UNCHECKED_BY_TOPIC = "Summary.getCountUncheckedByTopic";


    @NotBlank
    String question;

    @NotBlank
    String answer;

    @Column(name = "checked", nullable = false)
    private boolean check;

    @ManyToOne()
    @JoinColumn(name = "topic_id" )
    Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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

    public Summary() {
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
