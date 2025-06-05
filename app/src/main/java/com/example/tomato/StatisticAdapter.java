package com.example.tomato;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {
    private List<Statistic> statistics;

    public StatisticAdapter(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    @NonNull
    @Override
    public StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_statistic, parent, false);
        return new StatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticViewHolder holder, int position) {
        Statistic statistic = statistics.get(position);
        holder.nameTextView.setText(statistic.name);
        holder.durationTextView.setText(statistic.duration + " 分钟");
        holder.typeTextView.setText(statistic.type);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        holder.dateTextView.setText(sdf.format(statistic.date));
        // 动态设置内容描述
        holder.itemView.setContentDescription(
                "统计项：" + statistic.name + "，用时：" + statistic.duration + "分钟"
        );
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    static class StatisticViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, durationTextView, typeTextView,dateTextView;

        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
