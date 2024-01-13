package org.proxyrpk.pojo;

import java.util.Objects;

public class ExelServicesPOJO {
    // Класс
    private Integer numberClass;
    // Базовый номер
    private Integer basicNumber;
    // Изменен ВОИС
    private Boolean modify;
    // Наименование товаров и услуг на русском
    private String ruName;
    // Наименование товаров и услуг на английском
    private String enName;

    public ExelServicesPOJO(Integer numberClass, Integer basicNumber, Boolean modify, String ruName, String enName) {
        this.numberClass = numberClass;
        this.basicNumber = basicNumber;
        this.modify = modify;
        this.ruName = ruName;
        this.enName = enName;
    }

    public ExelServicesPOJO() {
    }

    public Integer getNumberClass() {
        return numberClass;
    }

    public void setNumberClass(Integer numberClass) {
        this.numberClass = numberClass;
    }

    public Integer getBasicNumber() {
        return basicNumber;
    }

    public void setBasicNumber(Integer basicNumber) {
        this.basicNumber = basicNumber;
    }

    public Boolean getModify() {
        return modify;
    }

    public void setModify(Boolean modify) {
        this.modify = modify;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Override
    public String toString() {
        return "ExelServicesPOJO{" +
                "numberClass=" + numberClass +
                ", basicNumber=" + basicNumber +
                ", modify=" + modify +
                ", ruName='" + ruName + '\'' +
                ", enName='" + enName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExelServicesPOJO that = (ExelServicesPOJO) o;

        if (!Objects.equals(numberClass, that.numberClass)) return false;
        if (!Objects.equals(basicNumber, that.basicNumber)) return false;
        if (!Objects.equals(modify, that.modify)) return false;
        if (!Objects.equals(ruName, that.ruName)) return false;
        return Objects.equals(enName, that.enName);
    }

    @Override
    public int hashCode() {
        int result = numberClass != null ? numberClass.hashCode() : 0;
        result = 31 * result + (basicNumber != null ? basicNumber.hashCode() : 0);
        result = 31 * result + (modify != null ? modify.hashCode() : 0);
        result = 31 * result + (ruName != null ? ruName.hashCode() : 0);
        result = 31 * result + (enName != null ? enName.hashCode() : 0);
        return result;
    }
}
// Пример 1
//0: 42
//1: 420289
//2: X
//3: �������������� ������� ����������, ������� � ������������, ���������� ������ ������� ��������
//4: providing scientific information, advice and consultancy relating to net zero emissions

// Пример 2
//0: 42
//1: 420288
//2:
//3: �������������� ����� ��� ������
//4: golf course design
