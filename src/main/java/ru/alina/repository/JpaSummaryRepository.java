package ru.alina.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaSummaryRepository implements SummaryRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public Summary save(Summary summary, int topicId, int userId) {
        summary.setUser(em.getReference(User.class, userId));
        summary.setTopic(em.getReference(Topic.class, topicId));
        if (summary.isNew()) {
            em.persist(summary);
            return summary;
        } else if (get(summary.getId(), userId) == null) {
            return null;
        }
        return em.merge(summary);
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Summary.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Summary get(int id, int userId) {
        Summary summary = em.find(Summary.class, id);
        return summary != null && summary.getUser().getId() == userId ? summary : null;
    }

    @Override
    public Summary getWithTopic(int id, int userId) {
        return em.createNamedQuery(Summary.GET_WITH_TOPIC, Summary.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public List<Summary> getAll(int userId) {
        return em.createNamedQuery(Summary.ALL, Summary.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public long countChecked(int userId) {
        return em.createNamedQuery(Summary.COUNT_CHECKED, Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public List<Summary> getByTopic(int topicId, int userId) {
        return em.createNamedQuery(Summary.BELONG_TOPIC, Summary.class)
                .setParameter("userId", userId)
                .setParameter("topicId", topicId)
                .getResultList();
    }

    @Override
    public List<Summary> getByTopicName(String topicName, int userId) {
        return em.createQuery(Summary.BY_TOPIC_NAME, Summary.class)
                .setParameter(1, userId)
                .setParameter(2, topicName)
                .getResultList();
    }

    @Override
    public Summary getRandomNotCheckedWithTopic(int userId) {
        Integer max = (Integer)
                em.createQuery("select max(s.id) from Summary s where s.user.id=:userId and s.check =false")
                        .setParameter("userId", userId)
                        .getSingleResult();



        Integer min = (Integer)
                em.createQuery("select min(s.id) from Summary s where s.user.id=:userId and s.check = false ")
                        .setParameter("userId", userId)
                        .getSingleResult();


        max -= min;

        int id = min + (int) (Math.random() * ++max);


        return (Summary) em.createQuery("SELECT s from Summary s JOIN FETCH s.topic where s.id >=:id and s.user.id=:userId and s.check =false ")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .setMaxResults(1)
                .getSingleResult();
    }


    @Override
    public List<Summary> getCheckedSummaryWithTopic(int userId) {
        List<Summary> summaries = em.createNamedQuery(Summary.CHECKED_SUMMARY_WITH_TOPIC, Summary.class)
                .setParameter("userId", userId)
                .getResultList();
        return summaries.size() == 0 ? null : summaries;
    }

    @Override
    public long getCount(int userId) {
        return em.createNamedQuery(Summary.COUNT, Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }


    @Override
    public long getCountCheckedByTopic(int topicId, int userId) {
        return em.createNamedQuery(Summary.COUNT_CHECKED_BY_TOPIC, Long.class)
                .setParameter("topicId", topicId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public long getCountUncheckedByTopic(int topicId, int userId) {
        return em.createNamedQuery(Summary.COUNT_UNCHECKED_BY_TOPIC, Long.class)
                .setParameter("topicId", topicId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

}
