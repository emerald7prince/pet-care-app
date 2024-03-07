package com.example.petcareapplication.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcareapplication.AnalysisAdapter;
import com.example.petcareapplication.AppDatabase;
import com.example.petcareapplication.R;
import com.example.petcareapplication.dao.AnalysisDao;
import com.example.petcareapplication.dao.PetDao;

public class AnalyzesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzes);

        long petId = getIntent().getExtras().getLong("petId");
        AppDatabase db = AppDatabase.getInstance(this);
        AnalysisDao analysisDao = db.analysisDao();
        PetDao petDao = db.petDao();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AnalysisAdapter(analysisDao.getAllByPetId(petId)));

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        TextView label = findViewById(R.id.petName);
        String textLabel = "Анализы питомца " + petDao.getNameById(petId);
        label.setText(textLabel);
    }
}
