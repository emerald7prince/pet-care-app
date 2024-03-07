package com.example.petcareapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcareapplication.AnalysisAdapter;
import com.example.petcareapplication.AppDatabase;
import com.example.petcareapplication.R;
import com.example.petcareapplication.ReminderAdapter;
import com.example.petcareapplication.dao.ReminderDao;
import com.example.petcareapplication.entities.Reminder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RemindersActivity extends AppCompatActivity {
    ReminderAdapter reminderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        long petId = getIntent().getExtras().getLong("petId");

        AppDatabase db = AppDatabase.getInstance(this);
        ReminderDao reminderDao = db.reminderDao();
        reminderAdapter = new ReminderAdapter(reminderDao.getAllByPetId(petId));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reminderAdapter);


        Button addNew = findViewById(R.id.addNew);
        addNew.setOnClickListener(v -> {
            Intent intent = new Intent(RemindersActivity.this,
                    NewReminderActivity.class);
            //TODO Временная extra
            intent.putExtra("petId", petId);
            startActivity(intent);
        });

        // Обновление текста напоминаний
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    for (int i = 0; i < reminderAdapter.getItemCount(); i++) {
                        reminderAdapter.notifyItemChanged(i);
                    };
                });
            }
        }, 0, 60000);

    }
}
