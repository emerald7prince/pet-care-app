package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.petcareapplication.entities.Pet;
import com.example.petcareapplication.secondaryEntities.PetPageInformation;

import java.util.List;

@Dao
public interface PetDao {

    @Query("SELECT * FROM pets")
    List<Pet> getAll();

    @Query("SELECT * FROM pets WHERE id = :id")
    Pet getById(long id);

    @Query("SELECT name FROM pets WHERE id = :id")
    String getNameById(long id);

    @Insert
    long insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);

    @Query("DELETE FROM pets")
    void deleteAll();

    @Query("SELECT pets.id, pets.name, pets.gender, pets.date_of_birth," +
            "pet_types.name AS pet_type_name, breeds.name AS breed_name " +
            "FROM pets, pet_types, breeds " +
            "WHERE pet_types.id == pets.pet_type_id AND breeds.id == pets.breed_id " +
            "AND pets.id = :id ")
    PetPageInformation getPetPageInformation(long id);
}
