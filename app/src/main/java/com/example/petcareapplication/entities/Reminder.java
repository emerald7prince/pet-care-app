package com.example.petcareapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.petcareapplication.converters.DateConverter;

import java.util.Date;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @ColumnInfo(name = "pet_id")
    private long petId;
    @TypeConverters({DateConverter.class})
    private Date date;
    private Boolean regular;
    @ColumnInfo(name = "activity_type_id")
    private long activityTypeId;

    private String comment;

    public Reminder(String name, long petId, Date date, Boolean regular, long activityTypeId,
                    String comment) {
        setName(name);
        setPetId(petId);
        setDate(date);
        setRegular(regular);
        setActivityTypeId(activityTypeId);
        setComment(comment);
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

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getRegular() {
        return regular;
    }

    public void setRegular(Boolean regular) {
        this.regular = regular;
    }

    public long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

}
