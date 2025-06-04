package com.example.tomato;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private MainActivity activity;
    private AppDatabase db;

    public TaskAdapter(List<Task> tasks, MainActivity activity) {
        this.tasks = tasks;
        this.activity = activity;
        this.db = AppDatabase.getDatabase(activity);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Task task = tasks.get(position);
        holder.taskNameTextView.setText(task.title);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.dueDateTextView.setText(sdf.format(task.dueDate));

        holder.completeButton.setOnClickListener(v -> {
            new AsyncTask<Task, Void, Void>() {
                @Override
                protected Void doInBackground(Task... tasks) {
                    db.taskDao().deleteTask(tasks[0]);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    tasks.remove(position);
                    notifyDataSetChanged();
                }
            }.execute(task);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView, dueDateTextView;
        Button completeButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            completeButton = itemView.findViewById(R.id.completeButton);
        }
    }
}
