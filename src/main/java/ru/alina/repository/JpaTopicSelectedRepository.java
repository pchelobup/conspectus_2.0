package ru.alina.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.Topic;
import ru.alina.model.TopicSelected;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class JpaTopicSelectedRepository implements TopicSelectedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public TopicSelected get(int userId) {
        return (TopicSelected) em.createQuery("SELECT ts from TopicSelected  ts WHERE  ts.user.id=:userId")
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public Topic getTopicSelected(int userId) {
        TopicSelected topicSelected = (TopicSelected) em.createQuery("SELECT t from TopicSelected t where t.user.id=:userId")
                .setParameter("userId", userId)
                .getSingleResult();
        return topicSelected.getTopic();
    }

    @Override
    public int getId(int userId) {
        return getTopicSelected(userId).getId();
    }

    @Override
    public void update(int topicId, int userId) {
        TopicSelected topicSelected = get(userId);
        topicSelected.setTopic(em.getReference(Topic.class, topicId));
        em.merge(topicSelected);
    }
}
