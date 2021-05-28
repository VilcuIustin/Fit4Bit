package com.example.fit4bit_v002;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.JsonArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListviewSubCategorii extends AppCompatActivity {

    ListView listView;
    ArrayList<String> numeAntrenamenteGrele;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategorii);
        numeAntrenamenteGrele= new ArrayList<>();
        int poz = getIntent().getIntExtra("position",0);
        String dificultate=getIntent().getStringExtra("dificultate");
        for(int i=1;i<=getIntent().getIntExtra("size",0);i++){
            numeAntrenamenteGrele.add("Antrenament de nivel "+dificultate+" "+i);
        }


        listView = this.findViewById(R.id.lista_agrele);
        ArrayAdapter<String> setareAdapter = new ArrayAdapter<>(this.getApplication(), android.R.layout.simple_list_item_1, numeAntrenamenteGrele);
        listView.setAdapter(setareAdapter);
        Comunication comunication= new Comunication(this.getBaseContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        Raspuns r=comunication.listaExercitii(poz,position+1);
                        if(r.getStatus()){
                            ObjectMapper o = new ObjectMapper();
                            CollectionType typeReference =
                                    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, String.class);



                            ArrayList<String> poze;
                            try {
                                poze=o.readValue(r.getRezultat().toString(), typeReference);

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                Looper.prepare();
                                Toast.makeText(ListviewSubCategorii.this, "A aparut o problema!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent i = new Intent(ListviewSubCategorii.this.getBaseContext(), AfisarePozaDefault.class);
                            for(int j=0;j<poze.size();j++){
                                poze.set(j,dificultate+(position+1)+"/"+poze.get(j));
                                Log.e("Imagini", "run: "+poze.get(j) );
                            }
                            i.putExtra("poze",poze);

                            startActivity(i);
                        }else{
                            Looper.prepare();
                            Toast.makeText(ListviewSubCategorii.this.getBaseContext(), r.getEroare(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                t.start();
            }
        });
    }

}
