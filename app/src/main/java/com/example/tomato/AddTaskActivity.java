package com.example.tomato;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private TextInputEditText taskNameEditText;
    private Button setDateButton, saveButton;
    private Date dueDate;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        db = AppDatabase.getDatabase(this);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        setDateButton = findViewById(R.id.setDateButton);
        saveButton = findViewById(R.id.saveButton);

        setDateButton.setOnClickListener(v -> showDatePickerDialog());
        saveButton.setOnClickListener(v -> saveTask());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    dueDate = selectedDate.getTime();
                    setDateButton.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void saveTask() {
        String taskName = taskNameEditText.getText().toString().trim();
        if (taskName.isEmpty()) {
            taskNameEditText.setError("请输入任务名称");
            return;
        }

        if (dueDate == null) {
            setDateButton.setError("请设置截止日期");
            return;
        }

        Task task = new Task(taskName, dueDate);

        new AsyncTask<Task, Void, Void>() {
            @Override
            protected Void doInBackground(Task... tasks) {
                db.taskDao().insertTask(tasks[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                finish();
            }
        }.execute(task);
    }
}
