package ru.alina.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.Topic;
import ru.alina.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaTopicRepository implements TopicRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Topic save(Topic topic, int userId) {
        topic.setUser(em.getReference(User.class, userId));
        if (topic.isNew()) {
            em.persist(topic);
            return topic;
        }
        else if(get(topic.getId(), userId) == null) {
            return null;
        }
        return em.merge(topic);

    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Topic.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() !=0;
    }

    @Override
    public Topic get(int id, int userId) {
        Topic topic = em.find(Topic.class, id);
        return topic != null && topic.getUser().getId() == userId ? topic : null;
    }

    @Override
    public List<Topic> getAll(int userId) {
        return em.createNamedQuery(Topic.ALL, Topic.class)
                .setParameter("userId", userId)
                .getResultList();

    }

}
