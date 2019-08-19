package com.github.antksk.kakaopay.tasks.task2.integration.region;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;
import com.github.antksk.kakaopay.tasks.task2.integration.AdministrativeDistrictCsvFile;
import com.github.antksk.kakaopay.tasks.task2.repository.ServiceRegionRepository;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.collect.ImmutableList.toImmutableList;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RegionToServiceRegionTest {
    @Autowired
    ServiceRegionRepository serviceRegionRepository;


    @BeforeEach
    public void save_all(){
        List<ServiceRegion> districts = AdministrativeDistrictCsvFile.load()
                                                                     .stream().map(AdministrativeDistrictCsvFile::entity)
                                                                     .collect(toImmutableList());

        serviceRegionRepository.saveAll(districts);
    }

    @Test
    public void save_test(){
        serviceRegionRepository.findByRegionContainingOrderByCode("경상남도").forEach(e->log.debug("{} {}", e.getCode(), e.getRegion()));

    }


}
