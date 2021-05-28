package com.example.fit4bit_v002;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditareAdaugareAntrenament extends AppCompatActivity {

    private Button adaugaEx;
    private RecyclerView recViewExercitii;
    private Button salvare;
    private Antrenament antrenament;
    private EditText numeAntrenament;
    private Comunication comunication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adauga_antrenament_fab);
        recViewExercitii = findViewById(R.id.reciclerviewadaugaexercitiu);
        adaugaEx = findViewById(R.id.butonadaugaexercitiu);
        salvare = findViewById(R.id.butonsalveazatotantrenamentul);
        comunication = new Comunication(this);
        antrenament = new Antrenament();
        AdaugaAntrenamentRecView adapterr = new AdaugaAntrenamentRecView(this);
        antrenament.setExercitii(new ArrayList<>());
        recViewExercitii.setLayoutManager(new LinearLayoutManager(this));
        recViewExercitii.setAdapter(adapterr);
        numeAntrenament = findViewById(R.id.edittextnumeantrenament);

        adaugaEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterr.addExercitiu(new Exercitiu());

            }
        });

        salvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                antrenament.setDenumire(numeAntrenament.getText().toString());
                if (antrenament.getDenumire() == null) {
                    Toast.makeText(EditareAdaugareAntrenament.this.getBaseContext(), "Numele exercitiilor sunt obligatoriu", Toast.LENGTH_SHORT).show();
                    return;
                } else if (antrenament.getDenumire().trim().length() == 0) {
                    Toast.makeText(EditareAdaugareAntrenament.this.getBaseContext(), "Numele antrenamentului este obligatoriu", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Exercitiu exercitiu : adapterr.getExercitii()) {

                    if (exercitiu.getDenumire() == null) {
                        System.out.println("sal");
                        Toast.makeText(EditareAdaugareAntrenament.this.getBaseContext(), "Numele exercitiilor sunt obligatorii", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (exercitiu.getDenumire().trim().length() == 0) {
                        Toast.makeText(EditareAdaugareAntrenament.this.getBaseContext(), "Numele exercitiilor sunt obligatorii", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                antrenament.setExercitii(adapterr.getExercitii());
                System.out.println(antrenament.getExercitii());
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        Raspuns raspuns = comunication.sendAdaugaAntrenament(antrenament);
                        if (raspuns.getStatus()) {
                            Looper.prepare();
                            Toast.makeText(EditareAdaugareAntrenament.this, "Antrenamentul a fost creat!", Toast.LENGTH_SHORT).show();
                            JSONObject j = (JSONObject) raspuns.getRezultat();
                            try {
                                antrenament.setId(Long.parseLong(j.get("id").toString()));
                                Intent data = new Intent();
                                data.putExtra("antrenament", antrenament);
                                EditareAdaugareAntrenament.this.setResult(RESULT_OK, data);
                                EditareAdaugareAntrenament.this.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Looper.prepare();
                            Toast.makeText(EditareAdaugareAntrenament.this, raspuns.getEroare(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                t.start();

            }
        });

    }


}
