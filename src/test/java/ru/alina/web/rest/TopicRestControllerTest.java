package ru.alina.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alina.web.JsonUtil;
import ru.alina.model.Topic;
import ru.alina.service.TopicService;
import ru.alina.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.alina.TopicData.*;


class TopicRestControllerTest extends RestControllerTest {

    private final static String URL = "/rest/topics/";

    @Autowired
    private TopicService topicService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL+TOPIC1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> match(getObj(result, Topic.class), TOPIC1));
    }

    @Test
    void delete() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.delete(URL + TOPIC1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> topicService.get(TOPIC1_ID, USER_ID));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> match(getObjs(result, Topic.class), TOPICS));
    }

    @Test
    void update() throws Exception {
        Topic updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(URL + TOPIC1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        match(topicService.get(TOPIC1_ID, USER_ID), updated);
    }

    @Test
    void create() throws Exception {
        Topic newTopic = getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTopic)));

        Topic created = getObj(action.andReturn(), Topic.class);
        int newId = created.getId();
        newTopic.setId(newId);
        match(created, newTopic);
        match(topicService.get(newId, USER_ID), newTopic);
    }
}