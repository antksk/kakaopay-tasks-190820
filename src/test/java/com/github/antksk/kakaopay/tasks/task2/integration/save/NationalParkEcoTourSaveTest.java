package com.github.antksk.kakaopay.tasks.task2.integration.save;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.stream.Collectors.toList;

@DisplayName("생태 정보 서비스 저장 테스트")
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NationalParkEcoTourSaveTest {

    @Autowired
    Task2SaveService task2SaveService;

    @Autowired
    ServiceRegionRepository serviceRegionRepository;


    @BeforeEach
    public void save_all(){
        List<ServiceRegion> districts = AdministrativeDistrictCsvFile.load()
                                                                     .stream().map(AdministrativeDistrictCsvFile::entity)
                                                                     .collect(toImmutableList());

        serviceRegionRepository.saveAll(districts);
    }

    private final List<NationalParkEcoTourCsvFile> nationalParkEcoTourCsvFiles = NationalParkEcoTourCsvFile.load();

    private final List<NationalParkEcoTour> nationalParkEcoTours = nationalParkEcoTourCsvFiles.stream().map(ToEntity::entity).collect(toList());




    @DisplayName("생태 정보 서비스 저장")
    @Test
    public void test2(){
        for (NationalParkEcoTour nationalParkEcoTour : nationalParkEcoTours) {
            // 행정 구역형태의 지역 정보로 변경
            FormalServiceRegion formalServiceRegion = task2SaveService.convertFormalServiceRegion(nationalParkEcoTour.formalRegion());
            nationalParkEcoTour.setFormalServiceRegion(formalServiceRegion);
            nationalParkEcoTour.setKeywordJson(task2SaveService.programIntroductionDetailsKeywordJson(nationalParkEcoTour.getProgramIntroductionDetails()));
        }

        task2SaveService.save(nationalParkEcoTours);
        FormalServiceRegion 강원도_원주시_소초면 = FormalServiceRegion.create("3202031", "강원도 원주시 소초면");
        Page<NationalParkEcoTour> nationalParkEcoTourPage = task2SaveService.findByFormalServiceRegion(강원도_원주시_소초면,
                                                                                           PageRequest.of(0, 20));


        log.debug("### total page : {} ", nationalParkEcoTourPage.getTotalPages() );
        log.debug("#### sort : {}", nationalParkEcoTourPage.getSort());
        nationalParkEcoTourPage.forEach(e->log.debug("  - {}", e));
    }


}
