package com.example.jack.seg_project;

public class Service {
    private String name;
    private double rate;

    public Service(String name,double rate){
        name = name;
        rate = rate;
    }

    public void setName(String name){
        name = this.name;
    }

    public void setRate(double rate){
        rate = this.rate;
    }

    public String getName(){
        return name;
    }

    public double getRate(){
        return rate;
    }

}
