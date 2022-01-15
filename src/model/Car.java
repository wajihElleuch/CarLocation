package model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private long id;
    private String mat;
    private String mark;
    private String color;
    private List<Contract> contracts=new ArrayList<>();


    public Car() {
    }

    public Car(String mat, String mark, String color) {
        this.mat = mat;
        this.mark = mark;
        this.color = color;
    }

    public Car(long id, String mat, String mark, String color) {
        this.id = id;
        this.mat = mat;
        this.mark = mark;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
