package com.example.petcareapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.petcareapplication.converters.DateConverter;

import java.util.Date;

@Entity(tableName = "pets")
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @ColumnInfo(name = "pet_type_id")
    private long petTypeId;
    private boolean gender;
    @ColumnInfo(name = "date_of_birth")
    @TypeConverters({DateConverter.class})
    private Date dateOfBirth;
    @ColumnInfo(name = "breed_id")
    private long breedId;

    public long getBreedId() {
        return breedId;
    }

    public void setBreedId(long breedId) {
        this.breedId = breedId;
    }

    public Pet(String name, long petTypeId, boolean gender, Date dateOfBirth, long breedId) {
        setName(name);
        setPetTypeId(petTypeId);
        setGender(gender);
        setDateOfBirth(dateOfBirth);
        setBreedId(breedId);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPetTypeId() {
        return this.petTypeId;
    }

    public boolean getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPetTypeId(long petTypeId) {
        this.petTypeId = petTypeId;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
