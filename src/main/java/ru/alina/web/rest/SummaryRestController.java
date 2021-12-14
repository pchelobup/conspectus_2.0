package ru.alina.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.alina.model.Summary;
import ru.alina.service.SummaryService;
import ru.alina.web.SecurityUtil;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/summaries")
public class SummaryRestController {
    SummaryService summaryService;

    @Autowired
    public void setSummaryService(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/{id}")
    public Summary get(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        return summaryService.get(id, userId);
    }

    @GetMapping("/with-topic/{id}")
    public Summary getWithTopic(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        return summaryService.getWithTopic(id, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        summaryService.delete(id, userId);

    }

    @GetMapping
    public List<Summary> getAll() {
        return summaryService.getAll(SecurityUtil.authUserId());
    }

    @GetMapping(value = "/by-topic")
    public List<Summary> getByTopic(@RequestParam Integer topic) {
        int userId = SecurityUtil.authUserId();
        return summaryService.getByTopic(topic, userId);
    }

   @GetMapping(value = "/with-topic/checked")
    public List<Summary> getChecked() {
        int userId = SecurityUtil.authUserId();
        return summaryService.getCheckedSummaryWithTopic(userId);
    }

    @GetMapping(value = "/with-topic/not-checked")
    public Summary getNotChecked() {
        int userId = SecurityUtil.authUserId();
        return summaryService.getRandomNotCheckedWithTopic(userId);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Summary summary) {
        int userId = SecurityUtil.authUserId();
        summaryService.update(summary, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Summary create(@RequestBody Summary summary, @RequestParam @NotNull Integer topic) {
        int userId = SecurityUtil.authUserId();
        return summaryService.create(summary, topic, userId);
    }
}
