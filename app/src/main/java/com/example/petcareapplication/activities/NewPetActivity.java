package com.example.petcareapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.petcareapplication.AppDatabase;
import com.example.petcareapplication.converters.GendersConverter;
import com.example.petcareapplication.dao.BreedDao;
import com.example.petcareapplication.entities.Pet;
import com.example.petcareapplication.dao.PetDao;
import com.example.petcareapplication.dao.PetTypeDao;
import com.example.petcareapplication.R;
import com.example.petcareapplication.entities.PetType;

import java.util.Calendar;


public class NewPetActivity extends AppCompatActivity {
    AppDatabase db;
    PetDao petDao;
    PetTypeDao petTypeDao;
    Calendar date;
    TextView dateOfBirth;
    BreedDao breedDao;
    Spinner spinnerBreed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        db = AppDatabase.getInstance(this);
        petDao = db.petDao();
        petTypeDao = db.petTypeDao();
        breedDao = db.breedDao();
        date = Calendar.getInstance();

        Button button = findViewById(R.id.createPet);
        EditText editText = findViewById(R.id.newPetName);
        Spinner spinnerGender = findViewById(R.id.spinnerGender);
        Spinner spinnerType = findViewById(R.id.spinnerPetType);
        spinnerBreed = findViewById(R.id.spinnerBreed);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PetType petType = petTypeDao.getByName(parent.getSelectedItem().toString());
                setBreedAdapter(petType.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateOfBirth = findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        // Заполнение значений для выбора пола
        spinnerGender.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        new String[]{"Самка", "Самец"}));

        // Заполнение значений для выбора типа животного
        spinnerType.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, petTypeDao.getAllTypes()));

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //TODO Создать напоминание о дне рождения
        button.setOnClickListener(v -> {
            Intent intent = new Intent(NewPetActivity.this, PetPageActivity.class);
            Pet pet = new Pet(editText.getText().toString(),
                    petTypeDao.getByName(spinnerType.getSelectedItem().toString()).getId(),
                    GendersConverter.getBooleanGender(spinnerGender.getSelectedItem().toString()),
                    date.getTime(), breedDao.getIdByName
                    (spinnerBreed.getSelectedItem().toString()));
            long petId = petDao.insert(pet);
            intent.putExtra("petId", petId);
            startActivity(intent);
            finish();
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (!v.getText().toString().equals("")) {
                button.setEnabled(true);
            }
            return false;
        });
    }

    private void setDate() {
        new DatePickerDialog(this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDateOfBirthLabel();
        },
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDateOfBirthLabel() {
        dateOfBirth.setText(DateUtils.formatDateTime
                (this, date.getTimeInMillis(),
                        DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        dateOfBirth.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    private void setBreedAdapter(long petTypeId) {
        spinnerBreed.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        breedDao.getAllBreedNamesOfType(petTypeId)));
    }
}
