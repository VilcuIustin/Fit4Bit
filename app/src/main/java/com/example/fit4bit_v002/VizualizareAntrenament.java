package com.example.fit4bit_v002;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.Serializable;

public class VizualizareAntrenament extends AppCompatActivity {

    private RecyclerView exercitiiRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizare_antrenament);
        exercitiiRecView = findViewById(R.id.reciclerViewVizualizareAntrenament);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LogoGigapack3));
        }
        Antrenament antrenament =(Antrenament)this.getIntent().getSerializableExtra("Antrenament");
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivity(intent);
        VizualizeazaAntrenamentRecView adapterr = new VizualizeazaAntrenamentRecView(antrenament.getExercitii(),this);
        exercitiiRecView.setLayoutManager(new LinearLayoutManager(this));
        exercitiiRecView.setAdapter(adapterr);



    }
}