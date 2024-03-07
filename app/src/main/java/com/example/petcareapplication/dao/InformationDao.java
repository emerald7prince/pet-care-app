package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.petcareapplication.entities.Information;

@Dao
public interface InformationDao {
    @Insert
    void insert(Information information);

    @Query("SELECT * from information WHERE id = :id")
    Information getById(long id);

    @Query("SELECT * from information WHERE pet_id = :id")
    Information getByPetId(long id);

    @Delete
    void delete(Information information);

    @Query("DELETE FROM information")
    void deleteAll();
}
