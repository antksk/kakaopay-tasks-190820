package com.github.antksk.kakaopay.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public interface CsvFile {
    String getFilePath();

    default File readValues() throws FileNotFoundException {
        return ResourceUtils.getFile(getFilePath());
    }

    CsvSchema getCsvSchema();
}
