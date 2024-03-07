package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.petcareapplication.entities.EventType;

import java.util.List;

@Dao
public interface EventTypeDao {
    @Insert
    long insert(EventType eventType);

    @Query("SELECT * FROM event_types")
    List<EventType> getAll();

    @Query("SELECT * FROM event_types WHERE name = :name")
    EventType getByName(String name);

    @Query("SELECT name FROM event_types")
    List<String> getAllEventTypes();

    @Delete
    void delete(EventType eventType);

    @Query("DELETE FROM event_types")
    void deleteAll();
}
