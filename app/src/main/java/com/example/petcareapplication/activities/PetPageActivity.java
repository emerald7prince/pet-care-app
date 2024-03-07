package com.example.petcareapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petcareapplication.AppDatabase;
import com.example.petcareapplication.R;
import com.example.petcareapplication.converters.DateConverter;
import com.example.petcareapplication.converters.GendersConverter;
import com.example.petcareapplication.dao.AnalysisDao;
import com.example.petcareapplication.dao.PetDao;
import com.example.petcareapplication.dao.ReminderDao;
import com.example.petcareapplication.entities.Analysis;
import com.example.petcareapplication.entities.Reminder;
import com.example.petcareapplication.secondaryEntities.PetPageInformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PetPageActivity extends AppCompatActivity {
    AppDatabase db;
    PetDao petDao;
    PetPageInformation petInformation;
    long petId;

    private static final String CHANNEL_ID = "Cat channel";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_page);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        TextView field;

        petId = getIntent().getExtras().getLong("petId");

        db = AppDatabase.getInstance(this);
        petDao = db.petDao();
        ReminderDao reminderDao = db.reminderDao();

        petInformation = petDao.getPetPageInformation(petId);

        // Имя
        field = findViewById(R.id.petName);
        field.setText(petInformation.name);

        // Тип
        field = findViewById(R.id.petType);
        field.setText(petInformation.petTypeName);

        // Пол
        field = findViewById(R.id.petGender);
        field.setText(GendersConverter.getStringGender(petInformation.gender));

        //Порода
        field = findViewById(R.id.petBreed);
        field.setText(petInformation.breedName);

        //Возраст
        field = findViewById(R.id.petAge);
        field.setText(getAge());

        // Дата рождения
        field = findViewById(R.id.petBirthday);
        String petBirthdayText = "Дата рождения: " + DateUtils.formatDateTime
                (this, petInformation.dateOfBirth.getTime(),
                        DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
        field.setText(petBirthdayText);

        // Напоминание
        //TODO Формат даты на местное время
        Reminder reminder = reminderDao.getPetPageReminder
                (petId, reminderDao.getMinimumDate(petId));
        View reminderBlock = findViewById(R.id.petPageReminder);
        if (reminder != null) {
            field = reminderBlock.findViewById(R.id.reminderName);
            field.setText(reminder.getName());
            field = reminderBlock.findViewById(R.id.reminderDate);
            Locale.setDefault(Locale.FRANCE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
            field.setText(new SimpleDateFormat().format(reminder.getDate()));
            field = reminderBlock.findViewById(R.id.reminderTimer);
            field.setText(DateConverter.diffTime(reminder.getDate()));
        } else {
            reminderBlock.setVisibility(View.INVISIBLE);
        }

        // Кнопка списка напоминаний
        Button openRemindersList = findViewById(R.id.openRemindersList);
        openRemindersList.setOnClickListener(v -> {
            Intent intent = new Intent(PetPageActivity.this,
                    RemindersActivity.class);
            intent.putExtra("petId", petId);
            startActivity(intent);
        });

        // Кнопка создания нового напоминания
        Button createReminder = findViewById(R.id.createReminder);
        createReminder.setOnClickListener(v -> {
            Intent intent = new Intent(PetPageActivity.this,
                    NewReminderActivity.class);
            intent.putExtra("petId", petId);
            startActivity(intent);
        });

        // Кнопка открытия списка анализов
        Button analyzes = findViewById(R.id.buttonAnalyzes);
        analyzes.setOnClickListener(v -> {
            Intent intent = new Intent(PetPageActivity.this,
                    AnalyzesActivity.class);
            intent.putExtra("petId", petId);
            startActivity(intent);
        });
    }

    private String getAge() {
        Calendar currentDate = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.setTime(petInformation.dateOfBirth);
        int years = currentDate.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        int months = currentDate.get(Calendar.MONTH) - date.get(Calendar.MONTH);
        int days = currentDate.get(Calendar.DAY_OF_MONTH) - date.get(Calendar.DAY_OF_MONTH);

        //TODO Добавить склонения
        if (years == 0) {
            if (months == 0) {
                return "Возраст: " + days + " дней";
            } else {
                return "Возраст: " +  months + " месяцев";
            }
        } else {
            return "Возраст: " +  years + " лет";
        }
    }
}
