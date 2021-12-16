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
import ru.alina.service.SummaryService;
import ru.alina.service.TopicService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/conspectus")
public class SummaryController {
    SummaryService summaryService;
    TopicService topicService;

    @Autowired
    public void setSummaryService(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }



    @GetMapping
    public String getListTopicWithSummaries(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("topics", topicService.getAll(userId));
        model.addAttribute("checkedCount", summaryService.countChecked(userId));
        model.addAttribute("count", summaryService.getCount(userId));
        return "home";
    }

    @GetMapping("/read")
    public String read(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        String topicIdS = request.getParameter("topicId");
        List<Summary> summaries;
        if (topicIdS == null) {
            summaries = summaryService.getAll(userId);
            model.addAttribute("topicName", "all topics");
        }
        else {
            int topiId = Integer.parseInt(topicIdS);
            summaries = summaryService.getByTopic(topiId, userId);
            model.addAttribute("topicName", topicService.get(topiId, userId).getName());
        }

        model.addAttribute("summaries", summaries);
        return "read";
    }

    @GetMapping("/add")
    public String add(Model model) {
        int userId = SecurityUtil.authUserId();
        List<Topic> topics = topicService.getAll(userId);

        if (topics == null) {
            return "redirect:/topic";
        }
        model.addAttribute("topics", topics);
        Topic topicSelected = topicService.getTopicSelected(userId);
        model.addAttribute("topicSelected", topicSelected);
        return "summaryAdd";
    }

    @GetMapping("/update")
    public String update(WebRequest request, Model model) {
        model.addAttribute("summary", summaryService.get(Integer.parseInt(Objects.requireNonNull(request.getParameter("sid"))), SecurityUtil.authUserId()));
        model.addAttribute("topics", topicService.getAll(SecurityUtil.authUserId()));
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
        Topic topic = topicService.get(topicId, userId);
        if (submit.equalsIgnoreCase("save")) {
            Summary summary = summaryService.get(sid, userId);
            summary.setQuestion(question);
            summary.setAnswer(answer);
            summary.setTopic(topic);
            summaryService.update(summary, userId);

        } else if (submit.equalsIgnoreCase("delete")) {
            summaryService.delete(sid, userId);

        }
        return "redirect:/conspectus/read?topicId=" + topicId;
    }

    @PostMapping("/add")
    public String add(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        Topic topic = topicService.get(topicId, userId);
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        Summary summary = new Summary(question, answer, false, topic);
        summaryService.create(summary, SecurityUtil.authUserId());
        model.addAttribute("topics", topicService.getAll(SecurityUtil.authUserId()));
        return "redirect:/conspectus/add";
    }
}
