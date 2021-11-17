package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.repository.SummaryRepository;
import ru.alina.repository.TopicRepository;
import ru.alina.to.Review;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;


@Controller
@SessionAttributes(value = "review", types = ru.alina.to.Review.class)
public class ReviewTopicController {

    TopicRepository topicRepository;
    SummaryRepository summaryRepository;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    @GetMapping("/reviewSettings")
    public String getSetting(Model model) {
        int userId = SecurityUtil.authUserId();
        List<Topic> topics = topicRepository.getAll(userId);
        model.addAttribute("topics", topics);
        return "reviewTopicSetting";
    }


    @GetMapping("/setReview")
    public String setReview(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        Queue<Summary> reviewList = new LinkedList<>(summaryRepository.getByTopic(topicId, userId));
        Review review = new Review(reviewList, topicRepository.get(topicId, userId).getName(), summaryRepository.getCountUncheckedByTopic(topicId, userId), summaryRepository.getCountCheckedByTopic(topicId, userId));
        model.addAttribute("review", review);
        return "redirect:/review";

    }

    @GetMapping("/review")
    public String review(@ModelAttribute Review review, Model model, SessionStatus status) {
        if (review.isEmpty()) {
            model.addAttribute("review", review);
            status.isComplete();
            return "reviewTopicResult";
        }
        return "reviewTopic";
    }

    @PostMapping("/review")
    public String review(@ModelAttribute Review review, WebRequest request) {
        Summary summary = review.poll();
        boolean answer = Boolean.parseBoolean(request.getParameter("btn"));
        if (summary.isCheck() != answer) {
            if (!answer) {
                summary.setCheck(false);
            } else {
                summary.setCheck(true);
                review.incrementRightAnswerUnchecked();
            }
            summaryRepository.save(summary, SecurityUtil.authUserId());
        } else if (summary.isCheck() && answer) {
            review.incrementRightAnswerChecked();
        }
        review.incrementNumber();

        return "redirect:/review";
    }

    @GetMapping("/review/answer")
    public String reviewAnswer() {
        return "reviewTopicAnswer";
    }
}
