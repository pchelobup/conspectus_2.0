package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alina.repository.TopicRepository;

@Controller
@RequestMapping("/")
public class UserController {

    TopicRepository topicRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping
    public String getStartPage() {
        return "index";
    }

    @PostMapping
    public String signIn(@RequestParam("userId") Integer userId) {
        SecurityUtil.setAuthUserId(userId);
        return  "redirect:/conspectus";
    }
}
