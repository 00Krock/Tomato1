package com.example.tomato;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

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
        TextView nameTextView, durationTextView, typeTextView;

        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
        }
    }
}
