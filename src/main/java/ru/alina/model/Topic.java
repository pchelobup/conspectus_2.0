package ru.alina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Summary> summaries;

    public Topic(String name) {
        super(null);
        this.name = name;
    }

    /* for test */
    public Topic(Integer id, String name) {
        super(id);
        this.name = name;
        this.user = null;
    }

    public Topic(Integer id, String name, User user) {
        super(id);
        this.name = name;
        this.user = user;
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

    public List<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<Summary> summaries) {
        this.summaries = summaries;
    }

    @Override
    public String toString() {
        return "Topics{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
