package com.example.tomato;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "pomodoro_sessions")
public class PomodoroSession {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String taskName;
    public int duration; // in minutes
    public Date date;

    public PomodoroSession(String taskName, int duration) {
        this.taskName = taskName;
        this.duration = duration;
        this.date = new Date();
    }
}