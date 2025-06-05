package com.example.tomato;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView statisticRecyclerView;
    private StatisticAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = AppDatabase.getDatabase(this);
        statisticRecyclerView = findViewById(R.id.statisticRecyclerView);
        statisticRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadStatistics();
    }

    private void loadStatistics() {
        new AsyncTask<Void, Void, List<Statistic>>() {
            @Override
            protected List<Statistic> doInBackground(Void... voids) {
                List<Statistic> statistics = new ArrayList<>();

                // 获取完成的番茄钟会话
                List<PomodoroSession> sessions = db.pomodoroDao().getAllSessions();
                for (PomodoroSession session : sessions) {
                    statistics.add(new Statistic(
                            session.taskName,
                            session.duration,
                            "POMODORO",
                            session.date
                    ));
                }

                // 获取完成的任务
                List<Task> completedTasks = db.taskDao().getCompletedTasks();
                for (Task task : completedTasks) {
                    // 假设每个任务默认花费30分钟（可以根据需要调整）
                    statistics.add(new Statistic(
                            task.title,
                            30, // 默认任务完成时间
                            "TASK",
                            task.dueDate
                    ));
                }

                return statistics;
            }

            @Override
            protected void onPostExecute(List<Statistic> statistics) {
                adapter = new StatisticAdapter(statistics);
                statisticRecyclerView.setAdapter(adapter);
            }
        }.execute();
    }
}