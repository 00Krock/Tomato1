package com.example.tomato;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private TaskAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_pomodoro) {
                startActivity(new Intent(this, PomodoroActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });

        db = AppDatabase.getDatabase(this);

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(new TaskAdapter(new ArrayList<>(), this));

        FloatingActionButton addButton = findViewById(R.id.addTaskButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        loadTasks();
    }


    @SuppressLint("StaticFieldLeak")
    public void loadTasks() {
        new AsyncTask<Void, Void, List<Task>>() {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                return db.taskDao().getIncompleteTasks();
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                adapter = new TaskAdapter(tasks, MainActivity.this);
                taskRecyclerView.setAdapter(adapter);
            }
        }.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
    public MainActivity() {
        Log.d("DEBUG", "MainActivity constructed");
    }

}