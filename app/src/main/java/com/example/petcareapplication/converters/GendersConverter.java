package com.example.petcareapplication.converters;

public class GendersConverter {
    public static boolean getBooleanGender(String gender) {
        return gender.equals("Самец");
    }

    public static String getStringGender(Boolean gender) {
        if (gender) {
            return "Самец";
        } else {
            return "Самка";
        }
    }
}
