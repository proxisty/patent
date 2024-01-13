package org.proxyrpk.read;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.proxyrpk.pojo.ExelServicesPOJO;
import org.proxyrpk.pojo.ResultClass;
import org.proxyrpk.pojo.WordService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ReadExcel {
    private static final int sheetTranslate = 0;
    private static boolean writeToEndFile = false;
    private final List<WordService> wordService;
    private final File exelWithTranslate;
    private final File resultTranslate;
    private List<Row> listRowsExel;

    public ReadExcel(List<WordService> wordServices, File exelWithTranslate, File resultTranslate) {
        this.wordService = wordServices;
        this.exelWithTranslate = exelWithTranslate;
        this.resultTranslate = resultTranslate;
    }

    // последовательно обрабатываем классы
    public void startProcess() {
        List<Integer> numbersClasses = wordService.stream().map(WordService::getNumberClass).toList(); // список номеров классов
        System.out.println("Список классов: " + numbersClasses);
        readInMemoryExel(); // записываем в память List<Row>
        for (WordService services : wordService) {
            List<ExelServicesPOJO> exelServicesPOJOList = findIntervalIndexForClass(services.getNumberClass());
            ResultClass resultClass = findTranslate(exelServicesPOJOList, services);
            writeResultInTxt(resultClass, services.getNumberClass());
        }
    }


    // записываем в память List<Row>
    private void readInMemoryExel() {
        try (InputStream inStream = new FileInputStream(exelWithTranslate);
             ReadableWorkbook wb = new ReadableWorkbook(inStream)) {
            wb.getSheet(sheetTranslate).ifPresent(sheet -> {
                try {
                    listRowsExel = sheet.read();
                    listRowsExel.remove(0); // удаляем Топик
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (FileNotFoundException e) {
            System.err.println("Файл " + exelWithTranslate.getName() + " не найден");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeResultInTxt(ResultClass resultClasses, Integer numberClass) {

        try(OutputStream os = new FileOutputStream(resultTranslate, writeToEndFile);
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            BufferedWriter bf = new BufferedWriter(osw)) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n------------------------------------------------------------------------------------------\n");
            sb.append("Класс ").append(numberClass).append("\n");
            sb.append("Найдено совпадение с одним вариантом в количестве ")
                    .append((long) resultClasses.getTranslateService().size()).append(" штук:\n");
            // Строка, где поиск нашел один вариант
            String stringOneServiceTranslate = resultClasses.getTranslateService().stream()
                    .flatMap(map -> map.values().stream())
                    .collect(Collectors.joining("; "));
            sb.append(stringOneServiceTranslate).append("\n\n");
            // Строка, где поиск нашел несколько вариантов
            List<String> stringSomeServiceTranslate = resultClasses.getSomeTranslateService().stream()
                    .map(map -> map.entrySet().stream()
                            .map(entry -> "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"\n")
                            .collect(Collectors.joining("; "))).toList();
            sb.append("Найдено несколько переводов в количестве ").append(stringSomeServiceTranslate.size())
                    .append(":\n").append(stringSomeServiceTranslate);

            // Строка, где не найдено ни одного перевода
            String withoutTranslate = String.join("; ", resultClasses.getWithoutTranslate());
            sb.append("\nНе найдено переводов в количестве ").append(resultClasses.getWithoutTranslate().size())
                    .append("\n").append(withoutTranslate);

            bf.write(String.valueOf(sb));
            writeToEndFile = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultClass findTranslate(List<ExelServicesPOJO> exelServicesPOJOList, WordService services) {
        ResultClass resultClass = new ResultClass();
        for (String servise : services.getServicesList()) {
            // услугу ищем в pojo.
            List<String> tempTranslateList = new ArrayList<>(); // добавляем переводы для ru word услуги
            Integer countTranslate = 0;
            for(int i = 0; i < exelServicesPOJOList.size(); i++) {
                if (countTranslate.equals(exelServicesPOJOList.get(i).getBasicNumber()) || servise.equals(exelServicesPOJOList.get(i).getRuName())) {
                    tempTranslateList.add(exelServicesPOJOList.get(i).getEnName());
                    countTranslate = exelServicesPOJOList.get(i).getBasicNumber();
                }
            }
            if(tempTranslateList.size()==1){ // добавляем если одна запись
                List<Map<String, String>> kek = resultClass.getTranslateService();
                kek.add(new HashMap<String, String>() {{ put(servise, tempTranslateList.get(0));}});
                resultClass.setTranslateService(kek);
            } else if(tempTranslateList.size()>1) {
                List<Map<String, String>> kek = resultClass.getSomeTranslateService();
                kek.add(new HashMap<String, String>()
                {{ put(servise, tempTranslateList.stream().map(Object::toString).collect(Collectors.joining("; ")));}});
                resultClass.setSomeTranslateService(kek);
            } else {
                List<String> kek = resultClass.getWithoutTranslate();
                kek.add(servise);
                resultClass.setWithoutTranslate(kek);
            }
        }
        return resultClass;
    }

    // возвращаем из класса
    public List<ExelServicesPOJO> findIntervalIndexForClass(Integer numberClass){
        List<ExelServicesPOJO> exelServicesClass = new ArrayList<ExelServicesPOJO>();
        for (Row cells : listRowsExel) {
            if(Integer.parseInt(cells.getCellText(0)) == numberClass) {
                exelServicesClass.add(new ExelServicesPOJO(
                        Integer.parseInt(cells.getCellText(0)),
                        Integer.parseInt(cells.getCellText(1)),
                        isModify(cells.getCellText(2)),
                        cells.getCellText(3),
                        cells.getCellText(4)));
            }
        }
        return exelServicesClass;
    }

    private Boolean isModify(String modify) {
        return !modify.isEmpty();
    }
}
