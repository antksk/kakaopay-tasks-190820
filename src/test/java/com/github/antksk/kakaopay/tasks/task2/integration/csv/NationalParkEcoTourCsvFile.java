package com.github.antksk.kakaopay.tasks.task2.integration.csv;

import java.util.List;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.antksk.kakaopay.tasks.AbstractCsvFile;
import com.github.antksk.kakaopay.tasks.CsvFile;
import com.github.antksk.kakaopay.tasks.task2.entity.ToEntity;
import com.github.antksk.kakaopay.tasks.task2.entity.NationalParkEcoTour;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.github.antksk.kakaopay.tasks.AbstractCsvFile.loadCsvFile;

@Slf4j
@Setter
@ToString
public class NationalParkEcoTourCsvFile implements ToEntity<NationalParkEcoTour> {
    private int row;
    @Getter
    private String region;


    private String programName;
    private String theme;
    private String programIntroduction;
    private String programIntroductionDetails;



    public static List<NationalParkEcoTourCsvFile> load() {
        return loadCsvFile(csvFile, NationalParkEcoTourCsvFile.class);
    }


    @Override
    public NationalParkEcoTour entity() {
        return  NationalParkEcoTour.builder()
               .region(region)
               .programName(programName)
               .theme(theme)
               .programIntroduction(programIntroduction)
               .programIntroductionDetails(programIntroductionDetails)
               .build();
    }




    private static final CsvFile csvFile = new CSV();

    @Getter
    private static final class CSV extends AbstractCsvFile {
        private final String filePath;
        private final CsvSchema csvSchema;

        private CSV(){
            filePath = "classpath:task2/integration/task2.csv";
            csvSchema = CsvSchema.builder()
                                  .addColumn("row", CsvSchema.ColumnType.NUMBER)
                                  .addColumn("programName", CsvSchema.ColumnType.STRING)
                                  .addColumn("theme", CsvSchema.ColumnType.STRING)
                                  .addColumn("region", CsvSchema.ColumnType.STRING)
                                  .addColumn("programIntroduction", CsvSchema.ColumnType.STRING)
                                  .addColumn("programIntroductionDetails", CsvSchema.ColumnType.STRING)
                                  .build()
                                  // .withoutColumns()
                                  .withSkipFirstDataRow(true);
        }
    }
}
