package com.example.fit4bit_v002;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AfisarePozaDefault extends AppCompatActivity {      //sa nu fie fragment
    private RecyclerView pozeDefaultRecView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afisare_poze_default);
        pozeDefaultRecView = findViewById(R.id.rec_view_cu_poze_antrenament_default);
        AfisarePozaDefaultRecView adapter = new AfisarePozaDefaultRecView(this);

        adapter.setListaPoze(getIntent().getStringArrayListExtra("poze"));
        pozeDefaultRecView.setAdapter(adapter);
        pozeDefaultRecView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));



    }
}
