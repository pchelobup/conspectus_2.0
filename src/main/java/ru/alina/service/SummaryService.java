package ru.alina.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Transactional
public class SummaryService {
    private final Logger log = LoggerFactory.getLogger(SummaryService.class);
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
        log.info("create {} for user {}", summary, userId);
        ValidationUtil.notNull(summary,"summary must not be null");
        int topicSelectedId = topicSelectedRepository.getTopicSelected(userId).getId();
        if (topiId != topicSelectedId) {
            Topic topic = topicRepository.get(topiId, userId);
            topicSelectedRepository.update(topic, userId);
        }
        return summaryRepository.create(summary, topiId, userId);
    }
    public void update (Summary summary, int userId) {
        log.info("update {} for user {}", summary, userId);
        ValidationUtil.notNull(summary,"summary must not be null");
        ValidationUtil.notFound(summaryRepository.update(summary, userId), summary.getId(), name);
    }

    public void delete(int id, int userId) {
        log.info("delete summary {} for user {}", id, userId);
        ValidationUtil.notFound(summaryRepository.delete(id, userId), id, name);
    }

    @Transactional(readOnly = true)
    public Summary get(int id, int userId) {
        log.info("get summary {} for user {}", id, userId);
        return ValidationUtil.notFoundAndReturn(summaryRepository.get(id, userId), id, name);
    }

    @Transactional(readOnly = true)
    public Summary getWithTopic(int id, int userId) {
        log.info("get summary with topic {} for user {}", id, userId);
        return summaryRepository.getWithTopic(id, userId);
    }

    // return null if empty
    @Transactional(readOnly = true)
    public List<Summary> getAll(int userId) {
        log.info("getAll summary for user {}", userId);
        List<Summary> summaries = summaryRepository.getAll(userId);
        return summaries.size()==0 ? null : summaries;
    }

    @Transactional(readOnly = true)
    public long countChecked(int userId) {
        log.info("get count checked summary for user {}", userId);
        return summaryRepository.countChecked(userId);
    }

    //null if empty
    @Transactional(readOnly = true)
    public List<Summary> getByTopic(int topicId, int userId) {
        log.info("get summary by topic {} for user {}", topicId, userId);
        List<Summary> summaries = summaryRepository.getByTopic(topicId, userId);
        return summaries.size()==0 ? null : summaries;
    }

    @Transactional(readOnly = true)
    public Summary getRandomNotCheckedWithTopic(int userId) {
        log.info("get random checked summary for user {}", userId);
        return summaryRepository.getRandomNotCheckedWithTopic(userId);
    }

    @Transactional(readOnly = true)
    public List<Summary> getCheckedSummaryWithTopic(int userId) {
        log.info("get all checked summary with topic for user {}", userId);
        return summaryRepository.getCheckedSummaryWithTopic(userId);

    }

    @Transactional(readOnly = true)
    public long getCount(int userId) {
        log.info("get count summary for user {}", userId);
        return summaryRepository.getCount(userId);
    }

    @Transactional(readOnly = true)
    public long getCountCheckedByTopic(int topicId, int userId) {
        log.info("get count checked summary by topic {} for user {}", topicId, userId);
        return summaryRepository.getCountCheckedByTopic(topicId, userId);
    }

    @Transactional(readOnly = true)
    public long getCountUncheckedByTopic(int topicId, int userId) {
        log.info("get count not checked summary by topic {} for user {}", topicId, userId);
        return summaryRepository.getCountUncheckedByTopic(topicId, userId);
    }


}
