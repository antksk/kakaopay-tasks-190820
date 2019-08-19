package com.github.antksk.kakaopay.tasks.task2.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.removeSpecialString;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("csv에 있는 서비스 지역 정보에 오탐지 가능성이 있는 문자열 제거 테스트")
public class NationalParkEcoTourRegionRemoveSpecialStringTest {

    @DisplayName("특수한 오탐지 문자 제거 테스트")
    @Test
    public void test(){
        assertThat(removeSpecialString("경상북도 경주시, ")).isEqualTo("경상북도 경주시");
        assertThat(removeSpecialString("경상북도 경주시,,,,, ")).isEqualTo("경상북도 경주시");
        assertThat(removeSpecialString("경상북도 경주시    ")).isEqualTo("경상북도 경주시");
        assertThat(removeSpecialString("강원도 원주시 소초면 학곡리 900번지")).isEqualTo("강원도 원주시 소초면 학곡리 900번지");
    }

    @Test
    public void test2(){
        log.debug("{}", StringUtils.quote("atb"));
    }
}
