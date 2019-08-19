package com.github.antksk.kakaopay.tasks.task2.integration.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.antksk.kakaopay.tasks.task2.controller.NationalParkEcoTourController;
import com.github.antksk.kakaopay.tasks.task2.controller.RegionParam;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("생태 정보 서비스  테스트")
@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(NationalParkEcoTourController.class)
public class NationalParkEcoTourControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/task2/region")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(objectMapper.writeValueAsBytes(new RegionParam("평창군")));

        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andDo(print());
    }
}
