package com.github.antksk.kakaopay.tasks;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCsvFile implements CsvFile {

    private static CsvMapper mapper = new CsvMapper();

    public static <T> List<T> loadCsvFile(CsvFile csvFile, Class<?> clazz) {
        try {
            CsvSchema csvSchema = csvFile.getCsvSchema();
            MappingIterator<T> readValues = mapper.readerFor(clazz).with(csvSchema).readValues(csvFile.readValues());
            return readValues.readAll();
        } catch (Exception e) {
            log.error("파일 읽기를 실패하여, 비어있는 List형태로 리턴합니다. = " + csvFile.getFilePath(), e);
            return Collections.emptyList();
        }
    }
}
