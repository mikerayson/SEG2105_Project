package com.example.mike9.seg2105_project;

public class Timeslot {
    private String day;
    private int startHour;
    private int finishHour;

    public Timeslot(){

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(int finishHour) {
        this.finishHour = finishHour;
    }

    public String toString(){
        String result = getDay() + ", from: " + getStartHour() + " to " + getFinishHour();
        return result;
    }
}
