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
import ru.alina.to.Check;
import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@RequestMapping("check")
public class CheckController {
    SummaryRepository summaryRepository;
    Check check;

    @Autowired
    public void setSummaryRepository(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    @Autowired
    public void setCheck(Check check) {
        this.check = check;
    }

    @PostConstruct
    protected void init() {

    }


    @GetMapping("/init")
    public String checkInit(Model model) {
        if (!check.isExist()) {
            int userId = SecurityUtil.authUserId();
            List<Summary> summaryList = summaryRepository.getCheckedSummary(userId);
            if (summaryList !=null){
                LinkedList<Summary> ss = new LinkedList(summaryList);
                Collections.shuffle(ss);
                check.setSummaries(ss);
                check.setNumber(1);
                check.setCount(summaryRepository.countChecked(userId));
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
                Summary summary = summaryRepository.get(sid, userId);
                summary.setCheck(false);
                summaryRepository.save(summary, userId);
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
            model.addAttribute("summary", summaryRepository.get(sid, userId));
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
