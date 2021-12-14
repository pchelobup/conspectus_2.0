package ru.alina.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.alina.model.Topic;
import ru.alina.service.TopicService;
import ru.alina.web.SecurityUtil;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/topics", produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicRestController {

    private TopicService topicService;

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{id}")
    public Topic get(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        System.out.println(userId + " userId");
        return topicService.get(id, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        topicService.delete(id, userId);

    }

    @GetMapping
    public List<Topic> getAll() {
        return topicService.getAll(SecurityUtil.authUserId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Topic topic) {
        int userId = SecurityUtil.authUserId();
        topicService.update(topic, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Topic create(@RequestBody Topic topic) {
        int userId = SecurityUtil.authUserId();
        return topicService.create(topic, userId);
    }
}
