package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ru.alina.model.Summary;
import ru.alina.repository.SummaryRepository;

import java.util.Objects;

@Controller
@RequestMapping(value = "/learn")
public class LearnController {
    SummaryRepository summaryRepository;

    @Autowired
    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    @GetMapping
    public String learn(Model model) {
        int userId = SecurityUtil.authUserId();
        long count = summaryRepository.getCount(userId);
        long checkedCount = summaryRepository.countChecked(userId);
        if (count==0){
            model.addAttribute("nothing", "noAdd");
        }
        else if (checkedCount == count){
            model.addAttribute("nothing", "allDone");
        }
        else {
            Summary summary = summaryRepository.getRandomNotChecked(userId);
            model.addAttribute("summary", summary);
            model.addAttribute("count", count);
            model.addAttribute("checkedCount", checkedCount);
        }
        return "learn";
    }

    @PostMapping
    public String learn(WebRequest request) {
        boolean know = Boolean.parseBoolean(request.getParameter("btn"));
        if (know) {
            int sid = Integer.parseInt(Objects.requireNonNull(request.getParameter("sid")));
            int userId = SecurityUtil.authUserId();
            Summary summary = summaryRepository.get(sid, userId);
            summary.setCheck(true);
            summaryRepository.save(summary, userId);
        }
        return "redirect:/learn";
    }

    @GetMapping("/answer")
    public String checkAnswer(WebRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        int sid = Integer.parseInt(Objects.requireNonNull(request.getParameter("sid")));
        long count = summaryRepository.getCount(userId);
        long checkedCount = summaryRepository.countChecked(userId);
        model.addAttribute("summary", summaryRepository.get(sid, userId));
        model.addAttribute("type", "learn");
        model.addAttribute("count", count);
        model.addAttribute("checkedCount", checkedCount);
        return "learnAnswer";
    }


}
