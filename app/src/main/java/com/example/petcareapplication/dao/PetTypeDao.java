package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.petcareapplication.entities.PetType;

import java.util.List;

@Dao
public interface PetTypeDao {

    @Query("SELECT * FROM pet_types")
    List<PetType> getAll();

    @Query("SELECT * FROM pet_types WHERE id = :id")
    PetType getById(long id);

    @Query("SELECT * FROM pet_types WHERE name = :name")
    PetType getByName(String name);

    @Query("SELECT name FROM pet_types")
    List<String> getAllTypes();

    @Insert
    long insert(PetType petType);

    @Update
    void update(PetType petType);

    @Delete
    void delete(PetType petType);

    @Query("DELETE FROM pet_types")
    void deleteAll();

}
