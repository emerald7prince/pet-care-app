package com.example.petcareapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet_types")
public class PetType {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public PetType(String name) {
        setName(name);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
