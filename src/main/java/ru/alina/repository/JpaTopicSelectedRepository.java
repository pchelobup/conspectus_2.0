package ru.alina.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.Topic;
import ru.alina.model.TopicSelected;
import ru.alina.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaTopicSelectedRepository implements TopicSelectedRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public TopicSelected get(int userId) {
        List<TopicSelected> list = em.createNamedQuery(TopicSelected.GET, TopicSelected.class)
                .setParameter("userId", userId)
                .getResultList();
        return list.size() == 0 ? null : list.get(0);
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

    @Override
    public TopicSelected create(TopicSelected topicSelected, int userId) {
        topicSelected.setUser(em.getReference(User.class, userId));
        em.persist(topicSelected);
        return topicSelected;
    }
}
