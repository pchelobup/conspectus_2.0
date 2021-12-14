package ru.alina.repository;

import org.springframework.dao.support.DataAccessUtils;
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
        List<TopicSelected> topicSelected =  em.createQuery("SELECT t from TopicSelected t where t.user.id=:userId")
                .setParameter("userId", userId)
                .getResultList();
        /*на случай если topic который был selected удалили*/
        if (topicSelected.size()==0) {

            return (Topic) em.createQuery("SELECT t from Topic t where t.user.id=?1")
                    .setParameter(1,userId)
                    .getResultList().get(0);
        }
        return DataAccessUtils.singleResult(topicSelected).getTopic();
    }

    @Override
    public int getId(int userId) {
        return getTopicSelected(userId).getId();
    }

    @Override
    public void update(Topic topic, int userId) {
        TopicSelected topicSelected = get(userId);
        topicSelected.setTopic(topic);
        em.merge(topicSelected);
    }

    @Override
    public TopicSelected create(TopicSelected topicSelected, int userId) {
        topicSelected.setUser(em.getReference(User.class, userId));
        em.persist(topicSelected);
        return topicSelected;
    }
}
