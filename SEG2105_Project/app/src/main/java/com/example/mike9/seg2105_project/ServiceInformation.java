package com.example.mike9.seg2105_project;

public class ServiceInformation {

    private String name;
    private String rate;

    public ServiceInformation() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String toString() {
        return this.getName() + ", " + this.getRate();
    }


}
