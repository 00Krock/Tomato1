package com.example.tomato;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id ;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    public String title;
    public boolean isCompleted = false;

    public Task(String title, Date dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }
}
