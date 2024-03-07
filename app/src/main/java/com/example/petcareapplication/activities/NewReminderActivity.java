package com.example.petcareapplication.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.petcareapplication.AppDatabase;
import com.example.petcareapplication.R;
import com.example.petcareapplication.dao.EventTypeDao;
import com.example.petcareapplication.dao.ReminderDao;
import com.example.petcareapplication.entities.Reminder;

import java.util.Calendar;
import java.util.Objects;

public class NewReminderActivity extends AppCompatActivity {
    //TODO Использовать один TextView
    Spinner spinnerEventType;
    AppDatabase db;
    ReminderDao reminderDao;
    EventTypeDao eventTypeDao;
    String reminderName;
    long petId;
    TextView reminderDate;
    TextView reminderTime;
    Calendar date;
    int time;

    //TODO Помимо даты выбор времени
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        date = Calendar.getInstance();

        db = AppDatabase.getInstance(this);
        //TODO Падает, если нет extra (из списка напоминаний)
        petId = getIntent().getExtras().getLong("petId");

        reminderDao = db.reminderDao();
        eventTypeDao = db.eventTypeDao();

        spinnerEventType = findViewById(R.id.spinnerActivityType);
        spinnerEventType.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        eventTypeDao.getAllEventTypes()));
        spinnerEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateReminderName(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //TODO Добавить вставку комментария
        //TODO Проверить, откуда открывается activity, чтобы вернуть подходящий (petPage/reminders)
        Button create = findViewById(R.id.createNewReminder);
        create.setOnClickListener(v -> {
            Intent intent = new Intent(NewReminderActivity.this,
                    PetPageActivity.class);
            Reminder reminder = new Reminder(reminderName, petId,  date.getTime(),false,
                    eventTypeDao.getByName
                            (spinnerEventType.getSelectedItem().toString()).getId(),
                    "");
            reminderDao.insert(reminder);
            new ReminderManager().setReminder(this, date);
            intent.putExtra("petId", petId);
            startActivity(intent);
            finish();
        });

        reminderDate = findViewById(R.id.reminderDate);
        reminderDate.setOnClickListener(v -> setDate());

        reminderTime = findViewById(R.id.reminderTime);
        reminderTime.setOnClickListener(v -> {
            setTime();
        });
    }

    private void generateReminderName(String typeName) {
        reminderName = "Погулять с питомцем";
        if (Objects.equals(typeName, "Покормить")) {
            reminderName = "Покормить питомца";
        }

    }

    private void setDate() {
        new DatePickerDialog(this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
                    date.set(Calendar.YEAR, year);
                    date.set(Calendar.MONTH, month);
                    //TODO Исправить
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    setReminderDateLabel();
                },
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setReminderDateLabel() {
        reminderDate.setText(DateUtils.formatDateTime
                (this, date.getTimeInMillis(),
                        DateUtils.FORMAT_NUMERIC_DATE));
        reminderDate.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    private void setTime() {
        new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                date.set(Calendar.HOUR, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                reminderTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }, 15, 00, true).show();
    }

    //TODO Исправить напоминания
    public class ReminderManager {

        // Метод для установки напоминания
        public void setReminder(Context context, Calendar date) {

            // Создаем объект AlarmManager и Intent для запуска PendingIntent
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);

            // Устанавливаем напоминание в указанное время
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
        }
    }

    // Определяем класс, который будет получать и обрабатывать сигналы от AlarmManager
    public class ReminderReceiver extends BroadcastReceiver {

        // Метод, который будет вызван при получении сигнала от AlarmManager
        @Override
        public void onReceive(Context context, Intent intent) {

            // создаем NotificationManager
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Создаем уведомление
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.dog)
                    .setContentTitle("Уход за питомцем")
                    .setContentText("Погулять с питомцем")
                    .setPriority(NotificationCompat.PRIORITY_HIGH) // устанавливаем высокий приоритет
                    .setAutoCancel(true); // закрываем уведомление после клика

            // Отображаем уведомление
            notificationManager.notify(0, builder.build());
        }
    }
}
