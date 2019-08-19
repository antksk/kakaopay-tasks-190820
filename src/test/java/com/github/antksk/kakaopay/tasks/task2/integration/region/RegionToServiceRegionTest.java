package com.github.antksk.kakaopay.tasks.task2.integration.region;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.antksk.kakaopay.tasks.task2.integration.CSVFileToH2DbUploader;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RegionToServiceRegionTest {
    @Autowired
    ServiceRegionRepository serviceRegionRepository;


    @BeforeEach
    public void save_all(){
        CSVFileToH2DbUploader.serviceRegion(serviceRegionRepository);
    }

    @DisplayName("행정구역 형태의 검색 테스트")
    @Test
    public void save_test(){
        serviceRegionRepository.findByRegionContainingOrderByCode("경상남도").forEach(e->log.debug("{} {}", e.getCode(), e.getRegion()));
    }
}
