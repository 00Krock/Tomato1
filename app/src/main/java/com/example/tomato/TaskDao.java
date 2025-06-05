package com.example.tomato;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY due_date ASC")
    List<Task> getAllTasks();

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY due_date ASC")
    List<Task> getIncompleteTasks();

    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY due_date DESC")
    List<Task> getCompletedTasks();

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);
}