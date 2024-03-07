package com.example.petcareapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petcareapplication.AppDatabase;
import com.example.petcareapplication.dao.AnalysisDao;
import com.example.petcareapplication.dao.BreedDao;
import com.example.petcareapplication.dao.EventTypeDao;
import com.example.petcareapplication.dao.InformationDao;
import com.example.petcareapplication.entities.Analysis;
import com.example.petcareapplication.entities.Breed;
import com.example.petcareapplication.entities.EventType;
import com.example.petcareapplication.entities.Pet;
import com.example.petcareapplication.dao.PetDao;
import com.example.petcareapplication.entities.PetType;
import com.example.petcareapplication.dao.PetTypeDao;
import com.example.petcareapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    PetDao petDao;
    PetTypeDao petTypeDao;
    BreedDao breedDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        petDao = db.petDao();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFFFF"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        List<Pet> currentPets = petDao.getAll();
        if (currentPets.size() == 0) {
            firstStart();
        } else {
            drawInterface(currentPets);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clearData) {
            petTypeDao = db.petTypeDao();
            breedDao = db.breedDao();
            EventTypeDao eventTypeDao = db.eventTypeDao();
            InformationDao informationDao = db.informationDao();
            AnalysisDao analysisDao = db.analysisDao();
            analysisDao.deleteAll();
            petDao.deleteAll();
            petTypeDao.deleteAll();
            breedDao.deleteAll();
            eventTypeDao.deleteAll();
            informationDao.deleteAll();
            firstStart();
        }

        return super.onOptionsItemSelected(item);
    }

    private void firstStart() {
        int w;
        int h;
        int marginTop;

        databaseInitialization();

        ViewGroup relativeLayout = findViewById(R.id.relativeLayoutMain);
        relativeLayout.removeAllViewsInLayout();

        // Заголовок приветствия
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        marginTop = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        params.setMargins(0, marginTop, 0, 0);

        TextView firstTitle = new TextView(this);
        firstTitle.setText("Добро пожаловать!");
        firstTitle.setTextSize(30);
        firstTitle.setId(R.id.firstTitle);
        firstTitle.setTextColor(ContextCompat.getColor(this, R.color.black));

        relativeLayout.addView(firstTitle, params);

        // Второе предложение приветствия
        params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, R.id.firstTitle);
        marginTop = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        params.setMargins(0, marginTop, 0, 0);

        TextView secondTitle = new TextView(this);
        secondTitle.setText("Добавьте Вашего первого питомца");
        secondTitle.setTextSize(23);
        secondTitle.setId(R.id.secondTitle);

        relativeLayout.addView(secondTitle, params);

        // Кнопка добавления первого питомца
        w = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 330, getResources().getDisplayMetrics());
        h = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        params = new RelativeLayout.LayoutParams(w, h);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, R.id.secondTitle);
        marginTop = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        params.setMargins(0, marginTop, 0, 0);

        Button button = new Button(new ContextThemeWrapper
                (this, R.style.SystemButtonStyle), null, R.style.SystemButtonStyle);
        button.setText("Добавить питомца");
        button.setTextSize(25);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewPetActivity.class);
            startActivity(intent);
            finish();
        });
        relativeLayout.addView(button, params);
    }

    private void databaseInitialization() {
        // Первичное заполнение таблицы типов и пород животных
        db = AppDatabase.getInstance(this);
        petTypeDao = db.petTypeDao();
        long petTypeId;

        if (petTypeDao.getAll().size() == 0) {
            String[] petTypeNames = new String[]{"Кот/Кошка", "Пёс/Собака", "Рыба", "Черепаха",
                    "Попугай", "Кролик", "Хомяк", "Морская свинка"};

            for (String petTypeName : petTypeNames) {
                PetType petType = new PetType(petTypeName);
                petTypeId = petTypeDao.insert(petType);
                addBreeds(petTypeName, petTypeId);
            }
        }

        // Первичное заполнение таблицы типов активностей
        String[] eventTypesNames = new String[]{"Погулять", "Покормить", "Поиграть",
        "Сводить к ветеринару", "Сводить к груммеру", "Постричь", "Постричь когти", "Дрессировать",
        "Дать лакомство", "Дать лекарство", "Сдать анализы", "Сделать привику", "Собственное"};

        EventTypeDao eventTypeDao = db.eventTypeDao();

        for (String eventTypeName : eventTypesNames) {
            EventType eventType = new EventType(eventTypeName);
            eventTypeDao.insert(eventType);
        }
    }

    private void addBreeds(String petTypeName, long petTypeId) {
        breedDao = db.breedDao();
        String[] breedsNames;
        switch (petTypeName) {
            case("Кот/Кошка"):
                breedsNames = getResources().getStringArray(R.array.cat_breeds);
                break;
            case("Пёс/Собака"):
                breedsNames = getResources().getStringArray(R.array.dog_breeds);
                break;
            case("Рыба"):
                breedsNames = getResources().getStringArray(R.array.fish_breeds);
                break;
            default:
                breedsNames = new String[]{"Домашний", "Без породы"};
                break;
        }
        for (String breedName : breedsNames) {
            Breed breed = new Breed(breedName, petTypeId);
            breedDao.insert(breed);
        }
    }

    private void drawInterface(List<Pet> currentPets) {
        // test
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        int marginTop = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        params.setMargins(0, marginTop, 0, 0);
    }
}
