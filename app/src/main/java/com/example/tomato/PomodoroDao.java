package com.example.tomato;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PomodoroDao {
    @Insert
    void insertSession(PomodoroSession session);

    @Query("SELECT * FROM pomodoro_sessions ORDER BY date DESC")
    List<PomodoroSession> getAllSessions();
}