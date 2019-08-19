package com.github.antksk.kakaopay.tasks.task2.integration.keyword;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.antksk.kakaopay.tasks.task2.entity.FormalServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;
import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.integration.AdministrativeDistrictCsvFile;
import com.github.antksk.kakaopay.tasks.task2.integration.NationalParkEcoTourCsvFile;
import com.github.antksk.kakaopay.tasks.task2.entity.ToEntity;
import com.github.antksk.kakaopay.tasks.task2.repository.NationalParkEcoTourRepository;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;
import com.github.antksk.kakaopay.tasks.task2.service.KeywordService;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

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
        List<ServiceRegion> districts = AdministrativeDistrictCsvFile.load()
                                                                     .stream().map(AdministrativeDistrictCsvFile::entity)
                                                                     .collect(toImmutableList());

        serviceRegionRepository.saveAll(districts);


        for (NationalParkEcoTour nationalParkEcoTour : nationalParkEcoTours) {
            // 행정 구역형태의 지역 정보로 변경
            Optional<String> optionalFormalRegion = nationalParkEcoTour.formalRegion();
            FormalServiceRegion formalServiceRegion = task2SaveService.convertFormalServiceRegion(optionalFormalRegion);
            nationalParkEcoTour.setFormalServiceRegion(formalServiceRegion);

        }

        task2SaveService.save(nationalParkEcoTours);
    }


    private final List<NationalParkEcoTourCsvFile> nationalParkEcoTourCsvFiles = NationalParkEcoTourCsvFile.load();

    private final List<NationalParkEcoTour> nationalParkEcoTours = nationalParkEcoTourCsvFiles.stream().map(ToEntity::entity).collect(toList());

    @DisplayName("각 영역별 키워드 생성 테스트")
    @Test
    public void test(){
        nationalParkEcoTours.forEach(e->{
            log.debug("program : {}\n - {}\n - {}\n - {}", e.getProgramName()
                ,  keywordService.analyze(e.getProgramName())
                ,  keywordService.analyze(e.getProgramIntroduction())
                ,  keywordService.analyze(e.getProgramIntroductionDetails())
            );
        });
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


        log.debug("### bbb : {}", collect);
    }


}
