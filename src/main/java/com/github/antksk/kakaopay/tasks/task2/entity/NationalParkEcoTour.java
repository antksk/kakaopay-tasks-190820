package com.github.antksk.kakaopay.tasks.task2.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.github.antksk.kakaopay.tasks.task2.SiSggEmd;
import com.github.antksk.kakaopay.tasks.task2.controller.json.Task2Json;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@EqualsAndHashCode
@Entity
/**
 * identity 전략과는 다르게 내부적으로,
 * em.persist() 호출 후 먼저 시컨스를 사용하여 식별자 조회 후
 * 조회한 식별자를 엔티티에 할당 한 후에 영속성 컨텍스트에 저장 이후 트랜잭션을 커밋 해서 필러시 함
 * - jpa p136
 */
@SequenceGenerator(
    name="ntl_park_seq_generator",
    sequenceName = "ntl_park_seq",
    initialValue = 1, allocationSize = 1
)
@Table(name="t_ntl_park")
public class NationalParkEcoTour implements Task2Json {
    @Id
    @GeneratedValue(
        generator = "ntl_park_seq_generator",
        strategy = GenerationType.SEQUENCE)
    private long id;


    @Column(name = "region", length = 50, nullable = false)
    private String region;


    /**
     * 프로그램 명
     */
    @Column(name ="prg_nm", length = 50, nullable = false)
    private String programName;

    /**
     * 테마별 분류
     */
    @Column(length = 100, nullable = false)
    private String theme;

    /**
     * 프로그램 소개
     */
    @Column(name="prg_intro", length = 300, nullable = false)
    private String programIntroduction;
    /**
     * 프로그램 소개 상세
     */
    @Column(name="prg_intro_dtl", length = 500)
    private String programIntroductionDetails;

    @Setter
    @Column(name="kyd_json", length = 1000)
    private String keywordJson;

    /**
     * csv파일에서 등록된 region 정보를 기준으로 행정구역 정보에 맞는 시구군읍면동 정보로 문자열표시
     * @return
     */
    public Optional<String> formalRegion(){
        return SiSggEmd.getFormalRegion(region);
    }

    @Setter
    @Enumerated
    private FormalServiceRegion formalServiceRegion;



    public int programIntroductionCount(String keyword){
        return StringUtils.countOccurrencesOf(programIntroduction, keyword);
    }

    public int programIntroductionDetailCount( String keyword) {
        // 프로그램 상세 정보는 내용 동사, 일반명사, 고유명사, 의존명사에 대해서 미리 키워드로 만들어 놓음
        return StringUtils.countOccurrencesOf(keywordJson, String.format("\"%s\"",keyword));
    }

    @Builder
    public NationalParkEcoTour(String region,
                               String programName,
                               String theme,
                               String programIntroduction,
                               String programIntroductionDetails) {
        this.region = region;
        this.programName = programName;
        this.theme = theme;
        this.programIntroduction = programIntroduction;
        this.programIntroductionDetails = programIntroductionDetails;
    }
}
