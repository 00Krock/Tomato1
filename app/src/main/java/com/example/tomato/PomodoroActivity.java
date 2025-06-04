package com.example.tomato;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PomodoroActivity extends AppCompatActivity {
    private TextView timerTextView;
    private Spinner durationSpinner;
    private Button startButton, stopButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        timerTextView = findViewById(R.id.timerTextView);
        durationSpinner = findViewById(R.id.durationSpinner);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        // 设置时长选项
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pomodoro_durations,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);

        startButton.setOnClickListener(v -> startTimer());
        stopButton.setOnClickListener(v -> stopTimer());
    }

    private void startTimer() {
        int selectedDuration = Integer.parseInt(durationSpinner.getSelectedItem().toString());
        timeLeftInMillis = selectedDuration * 60 * 1000;

        updateCountDownText();

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        durationSpinner.setEnabled(false);

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                durationSpinner.setEnabled(true);
            }
        }.start();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        durationSpinner.setEnabled(true);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}