package com.example.petcareapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.petcareapplication.dao.AnalysisDao;
import com.example.petcareapplication.dao.BreedDao;
import com.example.petcareapplication.dao.EventTypeDao;
import com.example.petcareapplication.dao.InformationDao;
import com.example.petcareapplication.dao.PetDao;
import com.example.petcareapplication.dao.PetTypeDao;
import com.example.petcareapplication.dao.ReminderDao;
import com.example.petcareapplication.entities.Analysis;
import com.example.petcareapplication.entities.Breed;
import com.example.petcareapplication.entities.EventType;
import com.example.petcareapplication.entities.Information;
import com.example.petcareapplication.entities.Pet;
import com.example.petcareapplication.entities.PetType;
import com.example.petcareapplication.entities.Reminder;

@Database
        (entities = {Pet.class, PetType.class, Breed.class,
                Information.class, Analysis.class, Reminder.class,
                EventType.class},
        version = 10)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract PetDao petDao();
    public abstract PetTypeDao petTypeDao();
    public abstract BreedDao breedDao();
    public abstract InformationDao informationDao();
    public abstract AnalysisDao analysisDao();
    public abstract ReminderDao reminderDao();
    public abstract EventTypeDao eventTypeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
