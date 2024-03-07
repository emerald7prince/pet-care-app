package com.example.petcareapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.petcareapplication.converters.DateConverter;

import java.util.Date;

@Entity(tableName = "analyzes")
public class Analysis {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "pet_id")
    private long petId;
    private String name;
    private String result;
    @TypeConverters({DateConverter.class})
    private Date actuality;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getActuality() {
        return actuality;
    }

    public void setActuality(Date actuality) {
        this.actuality = actuality;
    }
}