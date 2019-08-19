package com.github.antksk.kakaopay.tasks.task2.integration.region;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.antksk.kakaopay.tasks.task2.entity.ToEntity;
import com.github.antksk.kakaopay.tasks.task2.integration.CSVFileToH2DbUploader;
import com.github.antksk.kakaopay.tasks.task2.integration.csv.NationalParkEcoTourCsvFile;
import com.github.antksk.kakaopay.tasks.task2.repository.NationalParkEcoTourRepository;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;

import lombok.extern.slf4j.Slf4j;

@DisplayName("생태 정보 서비스 지역을 기준으로 공식적인 지역(행정구역) 정보 탐색 테스트")
@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class NationalParkEcoTourRegionFindTest {
    @Autowired
    NationalParkEcoTourRepository nationalParkEcoTourRepository;

    @Autowired
    ServiceRegionRepository serviceRegionRepository;

    @DisplayName("행정구역 지역 정보 저장")
    @BeforeEach
    public void save_service_region(){
        CSVFileToH2DbUploader.serviceRegion(serviceRegionRepository);
    }


    @DisplayName("이미 등록된 행정구역을 기반으로, 생태 정보 서비스 지역을 공식적인 지역(행정구역) 정보로 변경하여 검색 후 표시 ")
    @Test
    public void test(){

        List<NationalParkEcoTourCsvFile> nationalParkEcoTourCsvFiles = NationalParkEcoTourCsvFile.load();

        nationalParkEcoTourCsvFiles.stream().map(ToEntity::entity).forEach(e->{
            log.debug("{} ", e.getProgramName() );
            e.formalRegion()
             .ifPresent(o->serviceRegionRepository.findByRegionContainingOrderByCode(o).forEach(r->log.debug("  {}=>{}", r.getCode(), r.getRegion())));
        });
    }


}
