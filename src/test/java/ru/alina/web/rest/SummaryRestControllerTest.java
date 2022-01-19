package ru.alina.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alina.TopicData;
import ru.alina.web.JsonUtil;
import ru.alina.model.Summary;
import ru.alina.service.SummaryService;
import ru.alina.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.alina.SummaryData.*;

class SummaryRestControllerTest extends RestControllerTest {
    private final static String URL = "/rest/summaries/";

    @Autowired
    private SummaryService summaryService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + SUMMARY1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> match(getObj(result, Summary.class), SUMMARY1));
    }

    @Test
    void getWithTopic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "with-topic/" + SUMMARY1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> match(getObj(result, Summary.class), SUMMARY1))
                .andExpect(result -> TopicData.match(getObj(result, Summary.class).getTopic(), SUMMARY1_WITH_TOPIC.getTopic()));

    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + SUMMARY1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> summaryService.get(SUMMARY1_ID, USER_ID));

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> match(getObjs(result, Summary.class), SUMMARIES));
    }

    @Test
    void getByTopic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL+"by-topic?topic="+TOPIC_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> match(getObjs(result, Summary.class), SUMMARIES_ONE_TOPIC));
    }

    @Test
    void getChecked() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "with-topic/checked"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> match(getObjs(result, Summary.class), SUMMARIES_CHECKED));

    }

    @Test
    void getNotChecked() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "with-topic/not-checked"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> assertThat(SUMMARIES_NOT_CHECKED)
                        .contains(getObj(result, Summary.class)));
    }

    @Test
    void update() throws Exception {
        Summary updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(URL + SUMMARY1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        match(summaryService.get(SUMMARY1_ID, USER_ID), updated);

    }

    @Test
    void create() throws Exception {
        Summary newSummary = getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(URL+ "?topic="+TOPIC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newSummary)));

        Summary created = getObj(action.andReturn(), Summary.class);
        int newId = created.getId();
        newSummary.setId(newId);
        match(created, newSummary);
        match(summaryService.get(newId, USER_ID), newSummary);
    }
}