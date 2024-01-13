package org.proxyrpk.read;

import org.proxyrpk.pojo.WordService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//
public class ReaderService {
    private final File file;
    private List<WordService> wordServiceList;

    public ReaderService(File file, List<WordService> wordServiceList) {
        this.file = file;
        this.wordServiceList = wordServiceList;
    }

    public List<WordService> createListService() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (!currentLine.isEmpty()) {
                    wordServiceList.add(returnClassLine(currentLine));
                }
            }
            return wordServiceList;
        }
    }

    // Проверить, что строка начитается с класса по паттерну "\d\d -
    public WordService returnClassLine(String line) {
        List<String> servicesList = new ArrayList<String>();
        Pattern patternClass = Pattern.compile("\\d{2}\\s-\\s.+\\.");
        Matcher matcher = patternClass.matcher(line);
        Integer numberClass = null;
        if (matcher.find()) {
            String services = new String(line.substring(4, line.length() - 1).getBytes(), StandardCharsets.UTF_8);
            numberClass = Integer.parseInt(line.substring(0, 2));
            servicesList = Arrays.stream(services.split(";")).map(String::trim).collect(Collectors.toList());
        }
        return new WordService(numberClass, servicesList);
    }
}
