package com.github.antksk.kakaopay.tasks.task2.integration.keyword;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;
import com.github.antksk.kakaopay.tasks.task2.integration.CSVFileToH2DbUploader;
import com.github.antksk.kakaopay.tasks.task2.repository.NationalParkEcoTourRepository;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;
import com.github.antksk.kakaopay.tasks.task2.service.KeywordService;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@DisplayName("생태 정보 서비스  테스트")
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NationalParkEcoTourKeywordTest {

    @Autowired
    @Qualifier("komoranKeywordService")
    KeywordService keywordService;

    @Autowired
    Task2SaveService task2SaveService;

    @Autowired
    NationalParkEcoTourRepository nationalParkEcoTourRepository;

    @Autowired
    ServiceRegionRepository serviceRegionRepository;

    @DisplayName("행정구역 지역 정보 및 생태 정보 서비스 저장")
    @BeforeEach
    public void save_all(){
        CSVFileToH2DbUploader.serviceRegion(serviceRegionRepository);
        CSVFileToH2DbUploader.nationalParkEcoTour(task2SaveService);
    }



    @DisplayName("1페이지의 프로그램 소개 키워드 갯수 확인")
    @Test
    public void test2(){

        final String keyword = "세계문화유산";
        Page<NationalParkEcoTour> byProgramIntroductionContaining = nationalParkEcoTourRepository.findByProgramIntroductionContaining(
            keyword,
            PageRequest.of(0, 10));

        Map<FormalServiceRegion, Integer> collect = byProgramIntroductionContaining
                                                                                 .stream()
                                                                                 .collect(groupingBy(NationalParkEcoTour::getFormalServiceRegion,
                                                                                                     summingInt(e -> e.programIntroductionCount(keyword))));


        while(byProgramIntroductionContaining.hasNext()){
            Pageable pageable = byProgramIntroductionContaining.nextPageable();

            log.debug("{}", pageable);
        }
    }


    @DisplayName("전체의 프로그램 소개 키워드 갯수 확인")
    @Test
    public void test3(){

        final String keyword = "세계문화유산";
        Set<NationalParkEcoTour> byProgramIntroductionContaining = nationalParkEcoTourRepository.findByProgramIntroductionContaining(
            keyword);

        Map<FormalServiceRegion, Integer> collect = byProgramIntroductionContaining
            .stream()
            .collect(groupingBy(NationalParkEcoTour::getFormalServiceRegion,
                                summingInt(e -> e.programIntroductionCount(keyword))));



        log.debug("{}", collect);
    }


    @DisplayName("전체의 프로그램 소개 상세 키워드 갯수 확인")
    @Test
    public void test4(){

        final String keyword = "문화";
        Set<NationalParkEcoTour> byProgramIntroductionContainingDetail = nationalParkEcoTourRepository.findByKeywordJsonContaining(
            keyword);

        byProgramIntroductionContainingDetail.forEach(e->log.debug("k : {}, {}",e.getKeywordJson(), e.programIntroductionDetailCount(keyword)));
        int sum = byProgramIntroductionContainingDetail.stream().mapToInt(e -> e.programIntroductionDetailCount(keyword)).sum();
        log.debug("keyword : {} => {}", keyword, sum);
    }

}
