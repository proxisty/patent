package org.proxyrpk;

import org.proxyrpk.pojo.WordService;
import org.proxyrpk.read.ReadExcel;
import org.proxyrpk.read.ReaderService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        File fileServicesForTranslate = new File("E:\\code\\lesson\\excel.work\\src\\main\\resources\\newWordSmallClasses24");
        File exelWithTranslate = new File("E:\\code\\lesson\\excel.work\\src\\main\\resources\\mktu_12_24_2lang.xlsx");
        File resultTranslate = new File("E:\\code\\lesson\\excel.work\\src\\main\\resources\\result.txt");
        ReaderService readerService = new ReaderService(fileServicesForTranslate, new ArrayList<WordService>());
        List<WordService> wordService = readerService.createListService();
        StringBuilder st = new StringBuilder();
        st.append("\n\n\n");
        for (WordService service : wordService) {
            String kek = "Для класса " + service.getNumberClass() + " всего " + service.getCountServicesInClass() + " услуг.\n";
            st.append(kek);
        }
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println(st);
        System.out.println(wordService.get(0));

        ReadExcel readExcel = new ReadExcel(wordService, exelWithTranslate, resultTranslate);
        readExcel.startProcess();

        try(OutputStream os = new FileOutputStream(resultTranslate, true);
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            BufferedWriter bf = new BufferedWriter(osw)) {
            bf.write(String.valueOf(st));
        }

    }
}