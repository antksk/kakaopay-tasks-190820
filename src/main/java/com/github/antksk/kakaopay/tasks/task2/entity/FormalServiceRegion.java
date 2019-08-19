package com.github.antksk.kakaopay.tasks.task2.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FormalServiceRegion {
    @Column(name="svc_rgn_cd", length = 7)
    private String code;

    @EqualsAndHashCode.Exclude
    @Column(name="svc_rgn", length = 30)
    private String region; // 시도명 시군구명 읍면동명

    public FormalServiceRegion(String code, String region) {
        this.code = code;
        this.region = region;
    }

    public static FormalServiceRegion create(String code, String region){
        return new FormalServiceRegion(code, region);
    }

    public static Optional<FormalServiceRegion> ofOptional(String code, String region){
        if(StringUtils.isEmpty(code)) return Optional.empty();
        return Optional.of(create(code, region));
    }

    private static final FormalServiceRegion empty = new FormalServiceRegion("0000000", "서비스 지역 정보 검색되지 않음");

    public static FormalServiceRegion empty(){
        return empty;
    }
}
