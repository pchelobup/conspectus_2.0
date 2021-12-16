package ru.alina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.repository.SummaryRepository;
import ru.alina.repository.TopicSelectedRepository;
import ru.alina.util.ValidationUtil;

import java.util.List;

@Service
public class SummaryService {
    final String name = "Summary.class";
    SummaryRepository summaryRepository;

    TopicSelectedRepository topicSelectedRepository;

    @Autowired
    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    @Autowired
    public void setTopicSelectedRepository(TopicSelectedRepository topicSelectedRepository) {
        this.topicSelectedRepository = topicSelectedRepository;
    }

    public Summary create (Summary summary, int userId) {
        ValidationUtil.notNull(summary,"summary must not be null");
        int topicSelectedId = topicSelectedRepository.getTopicSelected(userId).getId();
        Topic topic = summary.getTopic();
        if (topic.getId() != topicSelectedId) {
            topicSelectedRepository.update(topic, userId);
        }
        return summaryRepository.save(summary, userId);
    }
    public void update (Summary summary, int userId) {
        ValidationUtil.notNull(summary,"summary must not be null");
        ValidationUtil.notFound(summaryRepository.save(summary, userId), summary.getId(), name);
    }

    public void delete(int id, int userId) {
        ValidationUtil.notFound(summaryRepository.delete(id, userId), id, name);
    }

    @Transactional(readOnly = true)
    public Summary get(int id, int userId) {
        return ValidationUtil.notFoundAndReturn(summaryRepository.get(id, userId), id, name);
    }

    // return null if empty
    public List<Summary> getAll(int userId) {
        List<Summary> summaries = summaryRepository.getAll(userId);
        return summaries.size()==0 ? null : summaries;
    }

    public long countChecked(int userId) {
        return summaryRepository.countChecked(userId);
    }

  /*  public long countAllQuestion(int userId) {
        return summaryRepository.countAllQuestion(userId);
    } */

    //null if empty
    public List<Summary> getByTopic(int topicId, int userId) {
        List<Summary> summaries = summaryRepository.getByTopic(topicId, userId);
        return summaries.size()==0 ? null : summaries;
    }

    public Summary getRandomNotChecked(int userId) {
        return summaryRepository.getRandomNotChecked(userId);
    }

    //null if empty
    public List<Summary> getCheckedSummary(int userId) {
        return summaryRepository.getCheckedSummary(userId);

    }

    public long getCount(int userId) {
        return summaryRepository.getCount(userId);
    }

    public long getCountCheckedByTopic(int topicId, int userId) {
        return summaryRepository.getCountCheckedByTopic(topicId, userId);
    }

    public long getCountUncheckedByTopic(int topicId, int userId){
        return summaryRepository.getCountUncheckedByTopic(topicId, userId);
    }


}
