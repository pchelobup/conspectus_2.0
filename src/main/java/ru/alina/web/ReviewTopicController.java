package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import ru.alina.model.Summary;
import ru.alina.model.Topic;
import ru.alina.service.SummaryService;
import ru.alina.service.TopicService;
import ru.alina.to.Review;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;


@Controller
@SessionAttributes(value = "review", types = ru.alina.to.Review.class)
public class ReviewTopicController {

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



    @GetMapping("/reviewSettings")
    public String getSetting(Model model) {
        int userId = SecurityUtil.authUserId();
        List<Topic> topics = topicService.getTopicWithNotEmptySummary(userId);
        if (topics==null) {
            model.addAttribute("nothing", "noAdd");
            model.addAttribute("type", "check");
        }
        else {
            model.addAttribute("topics", topics);
        }
        return "reviewTopicSetting";
    }


    @GetMapping("/setReview")
    public String setReview(WebRequest request, Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        int userId = SecurityUtil.authUserId();
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        Review reviewSession = (Review) session.getAttribute("review");
        /*если в сессии нет ревью или он есть но topicId е совпадает значит создаем новый так как юзер или еще не выбирал никакую тему или выбрал новую*/
        if ( reviewSession== null || (session.getAttribute("review") !=null && reviewSession.get().getTopic().getId() != topicId)) {
            Queue<Summary> reviewList = new LinkedList<>(summaryService.getByTopic(topicId, userId));
            Review newReview = new Review(reviewList, topicService.get(topicId, userId).getName(), summaryService.getCountUncheckedByTopic(topicId, userId), summaryService.getCountCheckedByTopic(topicId, userId));
            model.addAttribute("review", newReview);
        }
        return "redirect:/review";

    }

    @GetMapping("/review")
    public String review(@ModelAttribute Review review, Model model, SessionStatus status) {
        if (review.isEmpty()) {
            model.addAttribute("review", review);
            status.setComplete();
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      /*      HttpSession session = attr.getRequest().getSession(true);
            System.out.println("status us complete");
            System.out.println(session.getAttribute("review") + " review");*/

            return "reviewTopicResult";
        }
        return "reviewTopic";
    }

    @PostMapping("/review")
    public String review(@ModelAttribute Review review, WebRequest request) {
        /*проверям равны ди id topic and summary еслинет значит в другой влкдке уже прошли этот вопрос или сменили тему. Может лучше реализовать через equals summary???*/
        int sid = Integer.parseInt(Objects.requireNonNull(request.getParameter("sid")));
        int topicId = Integer.parseInt(Objects.requireNonNull(request.getParameter("topicId")));
        if (sid == review.get().getId() && topicId == review.get().getTopic().getId()) {
            Summary summary = review.poll();
            boolean answer = Boolean.parseBoolean(request.getParameter("btn"));
            if (summary.isCheck() != answer) {
                if (!answer) {
                    summary.setCheck(false);
                } else {
                    summary.setCheck(true);
                    review.incrementRightAnswerUnchecked();
                }
                summaryService.update(summary, SecurityUtil.authUserId());
            } else if (summary.isCheck() && answer) {
                review.incrementRightAnswerChecked();
            }
            review.incrementNumber();
        }
        return "redirect:/review";
    }

    @GetMapping("/review/answer")
    public String reviewAnswer() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        /*на случай если на момент ответа в другой вкладке прошли весь тест и не открыли новый*/
        if (session.getAttribute("review") !=null) {
            return "reviewTopicAnswer";
        }
        else {
            return "home";
        }
    }
}
