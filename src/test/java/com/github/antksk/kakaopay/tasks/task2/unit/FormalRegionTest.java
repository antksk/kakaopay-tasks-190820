package com.github.antksk.kakaopay.tasks.task2.unit;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.antksk.kakaopay.tasks.task2.SiSggEmd;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("임의의형식대로 등록된 지역(region)정보를 좀더 격식을 차린 일반적인 형태로 변경(대한민국 행정구역 형식) ")
public class FormalRegionTest {


    @DisplayName("서비스 지역 정보가 null이거나 공백일 경우 빈 리스트 전달")
    @Test
    public void abnormal_test(){
        assertThatFormalRegion(StringUtils.EMPTY).isEmpty();
        assertThatFormalRegion(null).isEmpty();
    }

    private OptionalAssert<String> assertThatFormalRegion(String region) {
        return assertThat(SiSggEmd.getFormalRegion(region));
    }

    @DisplayName("서비스 지역 정보에 대해 격식을 차린 일반적인 형태로 변경(대한민국 행정구역 형식)")
    @Test
    public void test() {
        assertThatFormalRegion("경상북도 청송군 부동면 상의리 406번지")
            .isPresent()
          .containsInstanceOf(String.class)
          .contains("경상북도 청송군 부동면");

        assertThatFormalRegion("강원도 평창군 일대")
            .isPresent()
            .containsInstanceOf(String.class)
            .contains("강원도 평창군");

        assertThatFormalRegion("경상남도 함양 추성마을")
            .isPresent()
            .containsInstanceOf(String.class)
            .contains("경상남도 함양");

        assertThatFormalRegion("전라남도 화순")
            .isPresent()
            .containsInstanceOf(String.class)
            .contains("전라남도 화순");


        assertThatFormalRegion("경상북도 경주시")
            .isPresent()
            .containsInstanceOf(String.class)
            .contains("경상북도 경주시");
    }

}
