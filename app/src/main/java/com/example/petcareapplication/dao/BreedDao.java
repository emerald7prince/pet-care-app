package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.petcareapplication.entities.Breed;

import java.util.List;

@Dao
public interface BreedDao {
    @Query("SELECT * FROM breeds")
    List<Breed> getAll();

    @Query("SELECT * FROM breeds WHERE id = :id")
    Breed getById(long id);

    @Query("SELECT * FROM breeds WHERE pet_type_id = :id")
    List<Breed> getAllOfType(long id);

    @Query("SELECT name FROM breeds WHERE pet_type_id = :id")
    List<String> getAllBreedNamesOfType(long id);

    @Query("SELECT id FROM breeds WHERE name = :name")
    long getIdByName(String name);

    @Insert
    void insert(Breed breed);

    @Update
    void update(Breed breed);

    @Delete
    void delete(Breed breed);

    @Query("DELETE FROM breeds")
    void deleteAll();
}
