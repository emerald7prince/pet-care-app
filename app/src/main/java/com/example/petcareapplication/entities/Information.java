package com.example.petcareapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "information")
public class Information {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "pet_id")
    private long petId;
    @ColumnInfo(name = "blood_type")
    private String bloodType;
    private float weight;
    private float height;
    @ColumnInfo(name = "skin_color")
    private String skinColor;
    @ColumnInfo(name = "eyes_color")
    private String eyesColor;
    @ColumnInfo(name = "last_doctor_visit_id")
    private long lastDoctorVisitId;
    @ColumnInfo(name = "last_analyzes_taken_id")
    private long lastAnalyzesTakenId;
    @ColumnInfo(name = "last_groomer_visit_id")
    private long lastGroomerVisitId;

    public Information(long petId, String bloodType, float weight, float height, String skinColor,
                       String eyesColor) {
        setPetId(petId);
        setBloodType(bloodType);
        setWeight(weight);
        setHeight(height);
        setSkinColor(skinColor);
        setEyesColor(eyesColor);
    }

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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getEyesColor() {
        return eyesColor;
    }

    public void setEyesColor(String eyesColor) {
        this.eyesColor = eyesColor;
    }

    public long getLastDoctorVisitId() {
        return lastDoctorVisitId;
    }

    public void setLastDoctorVisitId(long lastDoctorVisitId) {
        this.lastDoctorVisitId = lastDoctorVisitId;
    }

    public long getLastAnalyzesTakenId() {
        return lastAnalyzesTakenId;
    }

    public void setLastAnalyzesTakenId(long lastAnalyzesTakenId) {
        this.lastAnalyzesTakenId = lastAnalyzesTakenId;
    }

    public long getLastGroomerVisitId() {
        return lastGroomerVisitId;
    }

    public void setLastGroomerVisitId(long lastGroomer_visit_id) {
        this.lastGroomerVisitId = lastGroomer_visit_id;
    }
}
