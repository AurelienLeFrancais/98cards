package com.example.tpfinal;

public class AjouterScore {

    private String date;
    private int point;
    private int minute;
    private int seconds;

    public AjouterScore(String date, int points,int minute, int seconds){
        this.date = date;
        this.point = points;
        this.minute = minute;
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getPoints() {
        return point;
    }

    public String getDate() {
        return date;
    }

    public int getMinute() {
        return minute;
    }
}
