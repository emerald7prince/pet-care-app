package com.example.petcareapplication;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcareapplication.entities.Analysis;

import java.util.List;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {
    private List<Analysis> analyzes;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.analysis_element, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.largeTextView.setText(analyzes.get(position).getResult());
        holder.smallTextView.setText(analyzes.get(position).getName());
        holder.analysisDate.setText(DateUtils.formatDateTime
                (holder.analysisDate.getContext(), analyzes.get(position).getActuality().getTime(),
                        DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public int getItemCount() {
        return analyzes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView largeTextView = itemView.findViewById(R.id.textViewLarge);
        TextView smallTextView = itemView.findViewById(R.id.textViewSmall);
        TextView analysisDate = itemView.findViewById(R.id.analysisDate);
    }

    public AnalysisAdapter(List<Analysis> analyzes) {
        setAnalyzes(analyzes);
    }

    public List<Analysis> getAnalyzes() {
        return analyzes;
    }

    public void setAnalyzes(List<Analysis> analyzes) {
        this.analyzes = analyzes;
    }
}
