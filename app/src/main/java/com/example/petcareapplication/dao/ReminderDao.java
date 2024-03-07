package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.petcareapplication.entities.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert
    long insert(Reminder reminder);

    @Query("SELECT * FROM reminders")
    List<Reminder> getAll();

    @Query("SELECT * FROM reminders WHERE pet_id = :id")
    List<Reminder> getAllByPetId(long id);

    @Delete
    void delete(Reminder reminder);

    @Query("SELECT MIN(date) FROM reminders WHERE pet_id = :id")
    long getMinimumDate(long id);

    @Query("SELECT * FROM reminders WHERE pet_id =:id AND date =:date")
    Reminder getPetPageReminder(long id, long date);
}
