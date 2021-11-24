package ru.alina.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ru.alina.model.Summary;
import ru.alina.service.SummaryService;
import ru.alina.to.Check;
import java.util.*;

@Controller
@RequestMapping("check")
public class CheckController {
    SummaryService summaryService;
    Check check;

    @Autowired
    public void setSummaryService(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @Autowired
    public void setCheck(Check check) {
        this.check = check;
    }



    @GetMapping("/init")
    public String checkInit(Model model) {
        if (!check.isExist()) {
            int userId = SecurityUtil.authUserId();
            List<Summary> summaryList = summaryService.getCheckedSummary(userId);
            if (summaryList !=null){
                LinkedList<Summary> summaries = new LinkedList(summaryList);
                Collections.shuffle(summaries);
                check.setSummaries(summaries);
                check.setNumber(1);
                check.setCount(summaryService.countChecked(userId));
                check.setExist(true);
            }
            else {
                model.addAttribute("nothing", "noAdd");
                model.addAttribute("type", "check");
                return "checkMe";
            }

        }
        return "redirect:/check";
    }

    @GetMapping
    public String check(Model model) {
        if (check.getSummaries().size()>0) {
            Summary summary = check.get();
            model.addAttribute("summary", summary);
            model.addAttribute("number", check.getNumber());
            model.addAttribute("count", check.getCount());
        }
        else {
            int rightAnswer = check.getRightAnswer();
            long count = check.getCount();
            check.reset();
            model.addAttribute("rightAnswer", rightAnswer);
            model.addAttribute("count", count);
            model.addAttribute("nothing", "allDone");
            model.addAttribute("type", "check");
        }
        return "checkMe";
    }

    @PostMapping
    public String check(WebRequest request) {
        int sid = Integer.parseInt(Objects.requireNonNull(request.getParameter("sid")));
        /*если id одинаковый значит этотто же вопрос, если нет значит вопрос пролистали на другйо вкладке с ним уже ничего делать не нужно, нужно вернуть актуальный вопрос*/
        if (sid == check.get().getId()) {
            check.setNumber(check.getNumber()+1);
            boolean know = Boolean.parseBoolean(request.getParameter("btn"));
            if (!know) {
                int userId = SecurityUtil.authUserId();
                Summary summary = summaryService.get(sid, userId);
                summary.setCheck(false);
                summaryService.update(summary, userId);
            }
            else {
                check.incrementRightAnswer();
            }
            check.remove();
         }
        return "redirect:/check";

    }

    @GetMapping("/answer")
    public String answer(WebRequest request, Model model){
        int userId = SecurityUtil.authUserId();
        int sid = Integer.parseInt(Objects.requireNonNull(request.getParameter("sid")));
        LinkedList<Summary> summaries = check.getSummaries();
        if (summaries.getFirst().getId()==sid) {
            model.addAttribute("summary", summaryService.get(sid, userId));
            model.addAttribute("number", check.getNumber());
            model.addAttribute("count", check.getCount());
            return "checkAnswer";
        }

        else
        {
            return "redirect:/check";
        }
    }


}
