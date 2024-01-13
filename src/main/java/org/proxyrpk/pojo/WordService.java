package org.proxyrpk.pojo;

import java.util.List;

public class WordService {
    private Integer numberClass;
    private List<String> servicesList;

    public WordService(Integer numberClass, List<String> servicesList) {
        this.numberClass = numberClass;
        this.servicesList = servicesList;
    }

    public Integer getCountServicesInClass() {
        return servicesList.size();
    }

    public Integer getNumberClass() {
        return numberClass;
    }

    public void setNumberClass(Integer numberClass) {
        this.numberClass = numberClass;
    }

    public List<String> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<String> servicesList) {
        this.servicesList = servicesList;
    }

    @Override
    public String toString() {
        return "numberClass='" + numberClass + ", servicesList=" + servicesList + "}";
    }
}
