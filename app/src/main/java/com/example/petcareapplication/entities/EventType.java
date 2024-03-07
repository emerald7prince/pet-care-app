package com.example.petcareapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_types")
public class EventType {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public EventType(String name) {
        setName(name);
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
}
