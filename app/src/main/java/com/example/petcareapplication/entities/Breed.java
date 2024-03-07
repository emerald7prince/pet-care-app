package com.example.petcareapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "breeds")
public class Breed {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "pet_type_id")
    private long petTypeId;
    private String name;

    public Breed(String name, long petTypeId) {
        setName(name);
        setPetTypeId(petTypeId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(long petTypeId) {
        this.petTypeId = petTypeId;
    }
}
