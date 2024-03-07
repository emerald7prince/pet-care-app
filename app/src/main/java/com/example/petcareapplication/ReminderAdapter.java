package com.example.petcareapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcareapplication.converters.DateConverter;
import com.example.petcareapplication.entities.Reminder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private List<Reminder> reminders;

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_element, parent, false);
        return new ReminderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {
        //TODO setText
        Date remindersDate = reminders.get(position).getDate();
        holder.reminderName.setText(reminders.get(position).getName());
        //TODO Локальное время
        Locale.setDefault(Locale.FRANCE);
        //Locale locale = Locale.FRANCE;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa");
        holder.reminderDate.setText(new SimpleDateFormat().format(remindersDate));
        holder.reminderTimer.setText(DateConverter.diffTime(remindersDate));
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView reminderName = itemView.findViewById(R.id.reminderName);
        TextView reminderDate = itemView.findViewById(R.id.reminderDate);
        TextView reminderTimer = itemView.findViewById(R.id.reminderTimer);
    }

    public ReminderAdapter(List<Reminder> reminders) {
        setReminders(reminders);
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
