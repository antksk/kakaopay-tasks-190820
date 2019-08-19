package com.github.antksk.kakaopay.tasks.task2.integration.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.antksk.kakaopay.tasks.task2.integration.AdministrativeDistrictCsvFile;
import com.github.antksk.kakaopay.tasks.task2.integration.NationalParkEcoTourCsvFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("csv 파일 읽음")
public class CsvReadTest {
    @DisplayName("서버개발_사전과제2_2017년_국립공원_생태관광_정보")
    @Test
    public void test(){
        NationalParkEcoTourCsvFile.load().forEach(e->log.debug("{}", e.getRegion()));
    }


    @DisplayName("행정구역 코드 확인")
    @Test
    public void test2(){
        AdministrativeDistrictCsvFile.load().forEach(e-> System.out.println("'" + e.getRegion() + "', " + e.getAdministrativeDistrictCode()));
    }
}
