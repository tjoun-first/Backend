package com.newsmoa.app.util;

import com.newsmoa.app.dto.AssemblyMember;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class CsvUtil {
    private static List<AssemblyMember> data = null;
    
    private static void loadCsv() {
        try {
            InputStreamReader isr = new InputStreamReader(new ClassPathResource("static\\국회의원 현황.csv").getInputStream());
            try (CSVReader reader =
                         new CSVReaderBuilder(isr)
                                 .withSkipLines(1).build() //헤더 무시
            ) {
                data = reader.readAll().stream()
                        .map(token -> new AssemblyMember(
                                token[0], //사람 이름
                                token[1]  //정당 이름
                        ))
                        .toList();
            }
        }
        catch (IOException e){
            log.error("파일 로딩 실패. 파일 위치나 이름을 확인하세요.");
            e.printStackTrace();
        }
        catch (CsvException e){
            log.error("파일 형식이 잘못되었습니다. 파일 내용을 확인해주세요.");
            e.printStackTrace();
        }
    }
    
    public static List<AssemblyMember> getMember() {
        if(data == null){
            loadCsv();
        }
        return data;
    }
}
