package ru.alina.service;

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
@Transactional
public class TopicService {
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
        ValidationUtil.notNull(topic,"topic must not be null");
        Topic topicSave = topicRepository.save(topic, userId);
        saveUpdateTopicSelected(topic, userId);
        return topicSave;
    }

    public void update(Topic topic, int userId) {
        Assert.notNull(topic, "topic must not be null");
        ValidationUtil.notFound(topicRepository.save(topic, userId), topic.getId(), Topic.class.getSimpleName());
    }


    public void delete(int id, int userId) {
        ValidationUtil.notFound(topicRepository.delete(id, userId), id, name);
    }

    @Transactional(readOnly = true)
    public Topic get(int id, int userId){
        return ValidationUtil.notFoundAndReturn(topicRepository.get(id, userId), id, name);
    }

    public List<Topic> getAll(int userId){
        List<Topic> topics = topicRepository.getAll(userId);
        return topics.size()==0 ? null : topics;
    }

    public Topic getTopicSelected(int userId) {
        return topicSelectedRepository.getTopicSelected(userId);
    }

    public void saveUpdateTopicSelected(Topic topic, int userId) {
        ValidationUtil.notFound(topic.getUser().getId() == userId, topic.getId(), Topic.class.getSimpleName());
        TopicSelected topicSelected = topicSelectedRepository.get(userId);

        if (topicSelected==null){
            topicSelected = new TopicSelected();
            topicSelected.setTopic(topic);
            topicSelectedRepository.create(topicSelected, userId);
        }
        else {
            topicSelectedRepository.update(topic, userId);
        }
    }

    public List<Topic> getTopicWithNotEmptySummary(int userId) {
        List<Topic> topics =  topicRepository.getAll(userId).stream().filter(topic -> topic.getSummaries().size()>0)
                .collect(Collectors.toList());
        return topics.size()==0 ? null : topics;
    }

}
