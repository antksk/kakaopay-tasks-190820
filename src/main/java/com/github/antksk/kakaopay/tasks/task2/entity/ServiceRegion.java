package com.github.antksk.kakaopay.tasks.task2.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
@EqualsAndHashCode
@Table(name="t_svc_rgn")
public class ServiceRegion {
    @Id
    @Column(name="cd", length = 7)
    private String code;

    @EqualsAndHashCode.Exclude
    @Column(length = 2, nullable = false)
    private String siCd;
    @EqualsAndHashCode.Exclude
    @Column(length = 5)
    private String sggCd;
    @EqualsAndHashCode.Exclude
    @Column(length = 7)
    private String emdCd;
    @EqualsAndHashCode.Exclude

    @Column(name="rgn", length = 30)
    private String region; // 시도명 시군구명 읍면동명

    @Builder
    public ServiceRegion(String code,
                         String siCd,
                         String sggCd,
                         String emdCd,
                         String region) {
        this.code = code;
        this.siCd = siCd;
        this.sggCd = sggCd;
        this.emdCd = emdCd;
        this.region = region;
    }

    public FormalServiceRegion getFormalServiceRegion(){
        return FormalServiceRegion
            .ofOptional(code, region)
            .orElse(FormalServiceRegion.empty());
    }

}
