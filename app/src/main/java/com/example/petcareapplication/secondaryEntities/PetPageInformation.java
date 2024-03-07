package com.example.petcareapplication.secondaryEntities;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.petcareapplication.converters.DateConverter;

import java.util.Date;

public class PetPageInformation {
    public long id;
    public String name;
    public boolean gender;
    @ColumnInfo(name = "date_of_birth")
    @TypeConverters({DateConverter.class})
    public Date dateOfBirth;
    @ColumnInfo(name = "pet_type_name")
    public String petTypeName;
    @ColumnInfo(name = "breed_name")
    public String breedName;
}
