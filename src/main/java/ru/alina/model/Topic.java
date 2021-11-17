package ru.alina.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = Topic.DELETE, query = "DELETE from Topic t where t.id=:id and t.user.id=:userId"),
        @NamedQuery(name = Topic.ALL, query = "SELECT t from Topic t where t.user.id=:userId order by t.name")
})
@Entity
@Table(name = "topic")
public class Topic extends BaseEntity {

    public static final String DELETE = "Topic.delete";
    public static final String ALL = "Topic.getAllSorted";

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Topic(String name) {
        super(null);
        this.name = name;
    }

    public Topic() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Topics{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
