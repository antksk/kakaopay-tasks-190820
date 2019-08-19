package com.github.antksk.kakaopay.tasks.task2.integration.csv;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.antksk.kakaopay.tasks.AbstractCsvFile;
import com.github.antksk.kakaopay.tasks.CsvFile;
import com.github.antksk.kakaopay.tasks.task2.entity.ToEntity;
import com.github.antksk.kakaopay.tasks.task2.entity.ServiceRegion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.github.antksk.kakaopay.tasks.AbstractCsvFile.loadCsvFile;

@Slf4j
@Setter
@Getter
@ToString
public class AdministrativeDistrictCsvFile implements ToEntity<ServiceRegion> {
    private String siCd;
    private String siNm; // 시도명
    private String sggCd;
    private String sggNm; // 시군구명
    private String emdCd;
    private String emdNm; // 읍면동명

    @Override
    public ServiceRegion entity(){
        return ServiceRegion
            .builder()
                .code(getAdministrativeDistrictCode())
                 .siCd(getSiCd())
                 .sggCd(getSggCd())
                 .emdCd(getEmdCd())

                .region(getRegion())
            .build();
    }

    private boolean hasSiSggEmd() {
        return hasSggCd() && hasEmdCd();
    }

    public boolean hasSggCd(){
        return false == StringUtils.isEmpty(getSggCd());
    }

    public boolean hasEmdCd(){
        return false == StringUtils.isEmpty(getEmdCd());
    }

    public boolean hasNotEmdCd() { return false == hasEmdCd(); }

    public String getAdministrativeDistrictCode(){
        if( hasSiSggEmd() ) return getEmdCd();
        if( hasSggCd() && hasNotEmdCd() ) return String.format("%s00",getSggCd());
        return String.format("%s00000",getSiCd());
    }

    public String getRegion(){
        if( hasSiSggEmd() ) return String.join(" ", getSiNm(), getSggNm(), getEmdNm());
        if( hasSggCd() && hasNotEmdCd() ) return String.join(" ", getSiNm(), getSggNm());
        return getSiNm();
    }

    public static List<AdministrativeDistrictCsvFile> load() {
        return loadCsvFile(csvFile, AdministrativeDistrictCsvFile.class);
    }


    private static final CsvFile csvFile = new CSV();


    @Getter
    private static final class CSV extends AbstractCsvFile {
        private final String filePath;
        private final CsvSchema csvSchema;

        CSV(){
            filePath = "classpath:task2/integration/ad.csv";
            csvSchema = CsvSchema.builder()
                                  .addColumn("siCd", CsvSchema.ColumnType.STRING)
                                  .addColumn("siNm", CsvSchema.ColumnType.STRING)
                                  .addColumn("sggCd", CsvSchema.ColumnType.STRING)
                                  .addColumn("sggNm", CsvSchema.ColumnType.STRING)
                                  .addColumn("emdCd", CsvSchema.ColumnType.STRING)
                                  .addColumn("emdNm", CsvSchema.ColumnType.STRING)
                                  .build()
                                  .withSkipFirstDataRow(true);
        }
    }
}
