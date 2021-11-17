package ru.alina.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alina.repository.TopicRepository;

@Controller
@RequestMapping("/test")
public class SignIn {

    TopicRepository topicRepository;
    Logger log = LoggerFactory.getLogger(SignIn.class);

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping
    public String getStartPage() {
        return "index";
    }

    @PostMapping
    public String signIn(Model model) {
        model.addAttribute("topics", topicRepository.getAll(1));
        return "home";
    }
}
