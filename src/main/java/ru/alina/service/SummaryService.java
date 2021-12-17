package ru.alina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.repository.SummaryRepository;
import ru.alina.repository.TopicRepository;
import ru.alina.repository.TopicSelectedRepository;
import ru.alina.util.ValidationUtil;

import java.util.List;

@Service
public class SummaryService {
    final String name = "Summary.class";
    SummaryRepository summaryRepository;
    TopicRepository topicRepository;
    TopicSelectedRepository topicSelectedRepository;

    @Autowired
    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }
    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setTopicSelectedRepository(TopicSelectedRepository topicSelectedRepository) {
        this.topicSelectedRepository = topicSelectedRepository;
    }

    public Summary create (Summary summary, int topiId, int userId) {
        ValidationUtil.notNull(summary,"summary must not be null");
        int topicSelectedId = topicSelectedRepository.getTopicSelected(userId).getId();
        //Topic topic = summary.getTopic();
        if (topiId != topicSelectedId) {
            Topic topic = topicRepository.get(topiId, userId);
            topicSelectedRepository.update(topic, userId);
        }
        return summaryRepository.save(summary, topiId, userId);
    }
    public void update (Summary summary, int topicId, int userId) {
        ValidationUtil.notNull(summary,"summary must not be null");
        ValidationUtil.notFound(summaryRepository.save(summary, topicId, userId), summary.getId(), name);
    }

    public void delete(int id, int userId) {
        ValidationUtil.notFound(summaryRepository.delete(id, userId), id, name);
    }

    @Transactional(readOnly = true)
    public Summary get(int id, int userId) {
        return ValidationUtil.notFoundAndReturn(summaryRepository.get(id, userId), id, name);
    }

    @Transactional(readOnly = true)
    public Summary getWithTopic(int id, int userId) {
        return summaryRepository.getWithTopic(id, userId);
    }
    // return null if empty
    @Transactional(readOnly = true)
    public List<Summary> getAll(int userId) {
        List<Summary> summaries = summaryRepository.getAll(userId);
        return summaries.size()==0 ? null : summaries;
    }

    @Transactional(readOnly = true)
    public long countChecked(int userId) {
        return summaryRepository.countChecked(userId);
    }

    //null if empty
    @Transactional(readOnly = true)
    public List<Summary> getByTopic(int topicId, int userId) {
        List<Summary> summaries = summaryRepository.getByTopic(topicId, userId);
        return summaries.size()==0 ? null : summaries;
    }

    @Transactional(readOnly = true)
    public List<Summary> getByTopicName(String topicName, int userId) {
        List<Summary> summaries = summaryRepository.getByTopicName(topicName, userId);
        return summaries.size()==0 ? null : summaries;
    }

    @Transactional(readOnly = true)
    public Summary getRandomNotCheckedWithTopic(int userId) {
        return summaryRepository.getRandomNotCheckedWithTopic(userId);
    }

    @Transactional(readOnly = true)
    public List<Summary> getCheckedSummaryWithTopic(int userId) {
        return summaryRepository.getCheckedSummaryWithTopic(userId);

    }

    @Transactional(readOnly = true)
    public long getCount(int userId) {
        return summaryRepository.getCount(userId);
    }

    @Transactional(readOnly = true)
    public long getCountCheckedByTopic(int topicId, int userId) {
        return summaryRepository.getCountCheckedByTopic(topicId, userId);
    }

    @Transactional(readOnly = true)
    public long getCountUncheckedByTopic(int topicId, int userId){
        return summaryRepository.getCountUncheckedByTopic(topicId, userId);
    }


}
