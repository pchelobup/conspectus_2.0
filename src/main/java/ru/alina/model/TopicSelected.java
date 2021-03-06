package ru.alina.model;

import javax.persistence.*;
@NamedQueries({
        @NamedQuery(name = TopicSelected.GET, query = "SELECT ts from TopicSelected  ts WHERE  ts.user.id=:userId")
})
@Entity
@Table(name = "topic_selected")
public class TopicSelected extends BaseEntity {
    public final static String GET = "TopicSelected.get";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id" )
    Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    User user;

    public TopicSelected() {
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TopicSelected{" +
                "id=" + id +
                ", topic=" + topic +
                '}';
    }
}
