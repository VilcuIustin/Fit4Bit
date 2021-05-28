package com.example.fit4bit_v002;


import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class AdaugaAliment extends AppCompatActivity {

    private EditText nume, calorii, cantitate, proteine, carbohidrati, grasimi;
    private Button cancel, save;
    private Aliment aliment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adauga_aliment);
        nume = findViewById(R.id.aliment);
        calorii = findViewById(R.id.calorii);
        cantitate = findViewById(R.id.cantitate);
        proteine = findViewById(R.id.proteine);
        carbohidrati = findViewById(R.id.carbohidrati);
        grasimi = findViewById(R.id.grasimi);
        cancel = findViewById(R.id.anulare);
        save = findViewById(R.id.salvarea_aliment);
        cancel= findViewById(R.id.anulare);
        Comunication comunication = new Comunication(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Macronutrient macronutrient = new Macronutrient(
                        Double.parseDouble(proteine.getText().toString()),
                        Double.parseDouble(carbohidrati.getText().toString()),
                        Double.parseDouble(grasimi.getText().toString()));
                aliment = new Aliment(nume.getText().toString().trim(),
                        Integer.parseInt(calorii.getText().toString()),
                        Double.parseDouble(cantitate.getText().toString()),
                        macronutrient
                );
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        Raspuns r = comunication.adaugaAliment(aliment);
                        Looper.prepare();
                        if (r.getStatus()) {
                            Toast.makeText(AdaugaAliment.this, "Aliment adaugat!", Toast.LENGTH_SHORT).show();
                            AdaugaAliment.this.aliment.setId(Long.parseLong(r.getRezultat().toString()));
                            AdaugaAliment.this.setResult(RESULT_OK, new Intent().putExtra("aliment", (Serializable) AdaugaAliment.this.aliment));
                            AdaugaAliment.this.finish();
                        } else {
                            String s = r.getEroare();
                            Toast.makeText(AdaugaAliment.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                t.start();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(RESULT_CANCELED);
                finish();
            }
        });

    }


}