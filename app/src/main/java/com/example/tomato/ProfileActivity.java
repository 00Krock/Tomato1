package com.example.tomato;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView statisticRecyclerView;
    private StatisticAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        statisticRecyclerView = findViewById(R.id.statisticRecyclerView);
        statisticRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 模拟数据 - 实际应用中应从数据库获取
        List<Statistic> statistics = new ArrayList<>();
        statistics.add(new Statistic("完成数学作业", 120, "TASK"));
        statistics.add(new Statistic("番茄钟学习", 25, "POMODORO"));
        statistics.add(new Statistic("阅读书籍", 45, "TASK"));

        adapter = new StatisticAdapter(statistics);
        statisticRecyclerView.setAdapter(adapter);
    }
}
