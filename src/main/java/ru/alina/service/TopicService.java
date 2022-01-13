package ru.alina.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.alina.model.Topic;
import ru.alina.model.TopicSelected;
import ru.alina.repository.TopicRepository;
import ru.alina.repository.TopicSelectedRepository;
import ru.alina.util.ValidationUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TopicService {
    private final Logger log = LoggerFactory.getLogger(SummaryService.class);
    private static final String name = "Topic.class";
    TopicRepository topicRepository;
    TopicSelectedRepository topicSelectedRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setTopicSelectedRepository(TopicSelectedRepository topicSelectedRepository) {
        this.topicSelectedRepository = topicSelectedRepository;
    }

    @Transactional
    public Topic create(Topic topic, int userId) {
        log.info("create {} for user {}", topic, userId);
        ValidationUtil.notNull(topic,"topic must not be null");
        Topic topicSave = topicRepository.save(topic, userId);
        saveUpdateTopicSelected(topic, userId);
        return topicSave;
    }

    @Transactional
    public void update(Topic topic, int userId) {
        log.info("update {} for user {}", topic, userId);
        Assert.notNull(topic, "topic must not be null");
        ValidationUtil.notFound(topicRepository.save(topic, userId), topic.getId(), Topic.class.getSimpleName());
    }

    @Transactional
    public void delete(int id, int userId) {
        log.info("delete topic {} for user {}", id, userId);
        ValidationUtil.notFound(topicRepository.delete(id, userId), id, name);
    }

    public Topic get(int id, int userId) {
        log.info("get topic {} for user {}", id, userId);
        return ValidationUtil.notFoundAndReturn(topicRepository.get(id, userId), id, name);
    }

    public List<Topic> getAll(int userId) {
        log.info("getAll topic for user {}", userId);
        List<Topic> topics = topicRepository.getAll(userId);
        return topics.size()==0 ? null : topics;
    }

    public Topic getTopicSelected(int userId) {
        log.info("get selected topic for user {}", userId);
        return topicSelectedRepository.getTopicSelected(userId);
    }

    @Transactional
    public void saveUpdateTopicSelected(Topic topic, int userId) {
        ValidationUtil.notFound(topic.getUser().getId() == userId, topic.getId(), Topic.class.getSimpleName());
        TopicSelected topicSelected = topicSelectedRepository.get(userId);

        if (topicSelected==null){
            log.info("create topicSeleted {} for user {}", topic, userId);
            topicSelected = new TopicSelected();
            topicSelected.setTopic(topic);
            topicSelectedRepository.create(topicSelected, userId);
        }
        else {
            log.info("update topicSelected {} for user {}", topic, userId);
            topicSelectedRepository.update(topic, userId);
        }
    }

    public List<Topic> getTopicWithNotEmptySummary(int userId) {
        log.info("getAll topic with not empty summary for user {}", userId);
        List<Topic> topics =  topicRepository.getAll(userId).stream().filter(topic -> topic.getSummaries().size()>0)
                .collect(Collectors.toList());
        return topics.size()==0 ? null : topics;
    }

}
