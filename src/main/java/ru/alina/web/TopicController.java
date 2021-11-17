package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ru.alina.model.Topic;
import ru.alina.repository.TopicRepository;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/topic")
public class TopicController {

    TopicRepository topicRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping
    public String getTopic(Model model) {
        int userId = SecurityUtil.authUserId();
        List<Topic> topics = topicRepository.getAll(userId);
        model.addAttribute("topics", topics);
        model.addAttribute("topic", new Topic());
        return "topics";
    }

    @GetMapping("/delete")
    public String delete(WebRequest request) {
        int userId = SecurityUtil.authUserId();
        topicRepository.delete(Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId"))), userId);
        return "redirect:/topic";
    }

    @PostMapping("/add")
    public String add(Topic topic) {
        int userId = SecurityUtil.authUserId();
        topicRepository.save(topic, userId);
        return "redirect:/topic";
    }

    @GetMapping("/update")
    public String update(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        Topic topic = topicRepository.get(topicId, userId);
        model.addAttribute("topic", topic);
        return "topicEdit";
    }

    @PostMapping("/update")
    public String update(Topic topic) {
        int userId = SecurityUtil.authUserId();
        topicRepository.save(topic, userId);
        return "redirect:/topic";
    }

}


