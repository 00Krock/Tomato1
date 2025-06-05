package com.example.tomato;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class PomodoroActivity extends AppCompatActivity {
    // 视图组件
    private EditText taskNameEditText;
    private TextView timerTextView;
    private Button btnDecreaseTime, btnIncreaseTime, startButton, stopButton,pauseButton;

    // 计时相关变量
    private long timeLeftInMillis;
    private int selectedDuration = 25; // 默认25分钟
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        // 绑定视图
        taskNameEditText = findViewById(R.id.taskNameEditText);
        timerTextView = findViewById(R.id.timerTextView);
        btnDecreaseTime = findViewById(R.id.btnDecreaseTime);
        btnIncreaseTime = findViewById(R.id.btnIncreaseTime);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        pauseButton=findViewById(R.id.pauseButton);

        // 初始化显示
        updateTimerDisplay();

        // 按钮事件
        btnDecreaseTime.setOnClickListener(v -> adjustTime(-5));
        btnIncreaseTime.setOnClickListener(v -> adjustTime(5));
        startButton.setOnClickListener(v -> startTimer());
        stopButton.setOnClickListener(v -> stopTimer());
        pauseButton.setOnClickListener(v -> pauseOrResumeTimer());
    }

    // 调整时长（单位：分钟）
    private void adjustTime(int minutes) {
        if (!timerRunning) {
            selectedDuration += minutes;
            if (selectedDuration < 1) selectedDuration = 1; // 最少1分钟
            updateTimerDisplay();
        }
    }

    // 更新界面显示
    private void updateTimerDisplay() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeText;
        if (timerRunning) {
            timeText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        } else {
            timeText = String.format(Locale.getDefault(), "%02d:00", selectedDuration);
        }
        timerTextView.setText(timeText);
    }

    // 开始计时
    private void startTimer() {
        String taskName = taskNameEditText.getText().toString().trim();
        if (taskName.isEmpty()) {
            taskNameEditText.setError("请输入任务名称");
            return;
        }

        if (!timerRunning) {
            timeLeftInMillis = selectedDuration * 60 * 1000;

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerDisplay();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    timerTextView.setText("00:00");
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                    btnDecreaseTime.setEnabled(true);
                    btnIncreaseTime.setEnabled(true);

                    // 记录完成的番茄钟会话
                    PomodoroSession session = new PomodoroSession(taskName, selectedDuration);
                    new Thread(() -> {
                        AppDatabase.getDatabase(PomodoroActivity.this)
                                .pomodoroDao()
                                .insertSession(session);
                    }).start();
                }
            }.start();

            timerRunning = true;
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(true);
            btnDecreaseTime.setEnabled(false);
            btnIncreaseTime.setEnabled(false);
        }
    }
    private void pauseOrResumeTimer() {
        if (timerRunning) {
            if (!isPaused) {
                // 暂停计时器
                countDownTimer.cancel();
                pauseButton.setText("继续");
                isPaused = true;
            } else {
                // 继续计时器
                countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeLeftInMillis = millisUntilFinished;
                        updateTimerDisplay();
                    }

                    @Override
                    public void onFinish() {
                        timerRunning = false;
                        isPaused = false;
                        timerTextView.setText("00:00");
                        startButton.setEnabled(true);
                        stopButton.setEnabled(false);
                        pauseButton.setEnabled(false);
                        btnDecreaseTime.setEnabled(true);
                        btnIncreaseTime.setEnabled(true);
                    }
                }.start();

                pauseButton.setText("暂停");
                isPaused = false;
            }
        }
    }

    // 停止计时
    private void stopTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            isPaused=false;
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            pauseButton.setText("暂停");
            btnDecreaseTime.setEnabled(true);
            btnIncreaseTime.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}