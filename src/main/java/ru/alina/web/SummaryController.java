package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.repository.SummaryRepository;
import ru.alina.repository.TopicRepository;
import ru.alina.repository.TopicSelectedRepository;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/conspectus")
public class SummaryController {
    TopicRepository topicRepository;
    SummaryRepository summaryRepository;
    TopicSelectedRepository topicSelectedRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    @Autowired
    public void setTopicSelectedRepository(TopicSelectedRepository topicSelectedRepository) {
        this.topicSelectedRepository = topicSelectedRepository;
    }

    @GetMapping
    public String getListTopicWithSummaries(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("topics", topicRepository.getAll(userId));
        model.addAttribute("checkedCount", summaryRepository.countChecked(userId));
        model.addAttribute("count", summaryRepository.countAllQuestion(userId));
        return "home";
    }

    @GetMapping("/read")
    public String read(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        int topiId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        List<Summary> summaries = summaryRepository.getByTopic(topiId, userId);
        model.addAttribute("summaries", summaries);
        model.addAttribute("topicName", topicRepository.get(topiId, SecurityUtil.authUserId()).getName());
        return "read";
    }

    @GetMapping("/add")
    public String add(Model model) {
        int userId = SecurityUtil.authUserId();
        List<Topic> topics = topicRepository.getAll(userId);
        if (topics == null) {
            return "topics";
        }
        model.addAttribute("topics", topics);
        Topic topicSelected = topicSelectedRepository.getTopicSelected(userId);
        model.addAttribute("topicSelected", topicSelected);
        return "summaryAdd";
    }

    @GetMapping("/update")
    public String update(WebRequest request, Model model) {
        model.addAttribute("summary", summaryRepository.get(Integer.parseInt(request.getParameter("sid")), SecurityUtil.authUserId()));
        model.addAttribute("topics", topicRepository.getAll(SecurityUtil.authUserId()));
        return "summaryEdit";
    }

    @PostMapping("/update")
    public String update(WebRequest request) {
        int userId = SecurityUtil.authUserId();
        int sid = Integer.parseInt(Objects.requireNonNull(request.getParameter("sid")));
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        String submit = request.getParameter("button");
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        Topic topic = topicRepository.get(topicId, userId);
        if (submit.equalsIgnoreCase("save")) {
            Summary summary = summaryRepository.get(sid, userId);
            summary.setQuestion(question);
            summary.setAnswer(answer);
            summary.setTopic(topic);
            summaryRepository.save(summary, userId);

        } else if (submit.equalsIgnoreCase("delete")) {
            summaryRepository.delete(sid, userId);

        }
        return "redirect:/conspectus/read?topicId=" + topicId;
    }

    @PostMapping("/add")
    public String add(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        Topic topic = topicRepository.get(topicId, userId);
        if (topic.getId() != topicSelectedRepository.getId(SecurityUtil.authUserId())) {
            topicSelectedRepository.update(topic.getId(), SecurityUtil.authUserId());
        }
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        Summary summary = new Summary(question, answer, false, topic);
        summaryRepository.save(summary, SecurityUtil.authUserId());
        model.addAttribute("topics", topicRepository.getAll(SecurityUtil.authUserId()));
        return "redirect:/conspectus/add";
    }
}
