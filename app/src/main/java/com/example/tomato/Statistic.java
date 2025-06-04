package com.example.tomato;

import java.util.Date;

public class Statistic {
    public String name;
    public long duration; // in minutes
    public String type; // "TASK" or "POMODORO"
    public Date date;

    public Statistic(String name, long duration, String type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.date = new Date();
    }
}
