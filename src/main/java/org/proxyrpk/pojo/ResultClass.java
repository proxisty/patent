package org.proxyrpk.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultClass {
    // найден более одного перевода. Русский, Английский
    private List<Map<String, String>> someTranslateService = new ArrayList<>();
    // найден один перевод. Русский, Английский
    private List<Map<String, String>> translateService = new ArrayList<>();
    // нету переводов
    private List<String> withoutTranslate = new ArrayList<>();

    public ResultClass() {
    }

    public List<Map<String, String>> getSomeTranslateService() {
        return someTranslateService;
    }

    public void setSomeTranslateService(List<Map<String, String>> someTranslateService) {
        this.someTranslateService = someTranslateService;
    }

    public List<Map<String, String>> getTranslateService() {
        return translateService;
    }

    public void setTranslateService(List<Map<String, String>> translateService) {
        this.translateService = translateService;
    }

    public List<String> getWithoutTranslate() {
        return withoutTranslate;
    }

    public void setWithoutTranslate(List<String> withoutTranslate) {
        this.withoutTranslate = withoutTranslate;
    }

    @Override
    public String toString() {
        return "ResultClass{" +
                "someTranslateService=" + someTranslateService +
                ", translateService=" + translateService +
                ", withoutTranslate=" + withoutTranslate +
                '}';
    }

    public List<String> getAllValues() {
        return someTranslateService.stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toList());
    }
}
