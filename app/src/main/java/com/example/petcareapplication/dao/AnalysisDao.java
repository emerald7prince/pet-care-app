package com.example.petcareapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.petcareapplication.entities.Analysis;

import java.util.List;

@Dao
public interface AnalysisDao {
    @Insert
    void insert(Analysis analysis);

    @Delete
    void delete(Analysis analysis);

    @Query("SELECT * FROM analyzes WHERE pet_id = :id")
    List<Analysis> getAllByPetId(long id);

    @Query("DELETE FROM analyzes")
    void deleteAll();
}
