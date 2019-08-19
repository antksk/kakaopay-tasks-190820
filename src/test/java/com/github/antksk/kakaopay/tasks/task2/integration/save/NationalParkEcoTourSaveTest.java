package com.github.antksk.kakaopay.tasks.task2.integration.save;

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
import com.github.antksk.kakaopay.tasks.task2.integration.CSVFileToH2DbUploader;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;
import com.github.antksk.kakaopay.tasks.task2.service.Task2SaveService;

import lombok.extern.slf4j.Slf4j;

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
        CSVFileToH2DbUploader.serviceRegion(serviceRegionRepository);
        CSVFileToH2DbUploader.nationalParkEcoTour(task2SaveService);
    }

    @DisplayName("생태 정보 서비스 저장")
    @Test
    public void test(){
        FormalServiceRegion 강원도_원주시_소초면 = FormalServiceRegion.create("3202031", "강원도 원주시 소초면");
        Page<NationalParkEcoTour> nationalParkEcoTourPage = task2SaveService.findByFormalServiceRegion(강원도_원주시_소초면,
                                                                                           PageRequest.of(0, 20));


        log.debug("### total page : {} ", nationalParkEcoTourPage.getTotalPages() );
        log.debug("#### sort : {}", nationalParkEcoTourPage.getSort());
        nationalParkEcoTourPage.forEach(e->log.debug("  - {}", e));
    }


}
