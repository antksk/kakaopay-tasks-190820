package com.github.antksk.kakaopay.tasks.task2.integration.json;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.antksk.kakaopay.tasks.task2.controller.json.ProgramIntroductionCountDetailJson;
import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.integration.CSVFileToH2DbUploader;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.extern.slf4j.Slf4j;

@DisplayName("생태 정보 서비스  테스트")
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JsonTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ServiceRegionRepository serviceRegionRepository;

    @Autowired
    Task2SaveService task2SaveService;

    @BeforeEach
    public void saveH2(){
        CSVFileToH2DbUploader.serviceRegion(serviceRegionRepository);
        CSVFileToH2DbUploader.nationalParkEcoTour(task2SaveService);
    }

    /**
     *
     * “programs” : [
     *      {
     *          “prgm_name”:”오감만족! 오대산 에코 어드벤처 투어”,
     *          “theme”:”아동 청소년 체험학습”
     *      },
     *      {
     *              “prgm_name”:”오대산국립공원 힐링캠프”, “theme”:”숲 치유”
     *      },
     * @throws JsonProcessingException
     */
    @DisplayName("조회시 사전과제에 나온 형태로 JSON 출력")
    @Test
    public void test() throws JsonProcessingException {
        log.debug("{}", objectMapper.writeValueAsString(task2SaveService.findByRegion("평창군", PageRequest.of(0,20))));
    }


    @DisplayName("프로그램 소개 조회에 대한 JSON 출력")
    @Test
    public void test2() throws JsonProcessingException {
        log.debug("{}",objectMapper.writeValueAsString(task2SaveService.programIntroductionCount("세계문화유산")));
    }


    @DisplayName("프로그램 소개 상세 조회에 대한 JSON 출력")
    @Test
    public void test3() throws JsonProcessingException {
        final String keyword = "문화";
        int count = task2SaveService.programIntroductionCountDetail(keyword);
        log.debug("{}", objectMapper.writeValueAsString(ProgramIntroductionCountDetailJson.of(keyword, count)));
    }

}
