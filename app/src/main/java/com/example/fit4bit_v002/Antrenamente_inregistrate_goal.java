package com.example.fit4bit_v002;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Antrenamente_inregistrate_goal extends Fragment {

    private RecyclerView antrenamenteRecView;
    private FloatingActionButton floatingActionButtonAdaugaAntrenament;
    private  ArrayList<Antrenament> listaAntrenamente;
    private ArrayList<Exercitiu> exercitii;
    private Comunication comunication;
    private int code;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.antrenamente_inregistrate_goal, container, false);
        antrenamenteRecView = view.findViewById(R.id.antrenamenteRecView);
        this.getActivity().setTitle("Antrenamente");
        floatingActionButtonAdaugaAntrenament = view.findViewById(R.id.fabAdaugaAntrenament);
         exercitii = new ArrayList<>();
        comunication= new Comunication(this.getContext());
        listaAntrenamente = new ArrayList<>();
        Thread t = new Thread(){
            @Override
            public void run() {
                Raspuns r =  comunication.getAntrenamente();
                if(r.getStatus()){
                    JSONArray j= (JSONArray)r.getRezultat();
                    System.out.println(j);
                    try {
                        for(int i = 0 ; i<j.length();i++){
                            listaAntrenamente.add((Antrenament) comunication.parseData(j.getJSONObject(i),Antrenament.class));
                        }
                        Antrenamente_inregistrate_goal.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AntrenamenteRecViewAdapter adapterr = new AntrenamenteRecViewAdapter(Antrenamente_inregistrate_goal.this);
                                adapterr.setListaAntrenamente(listaAntrenamente);
                                antrenamenteRecView.setLayoutManager(new LinearLayoutManager(getContext()));
                                antrenamenteRecView.setAdapter(adapterr);
                            }
                        });

                        Log.e("TESTULET", listaAntrenamente.toString() );
                    } catch (JsonProcessingException | JSONException e) {
                        e.printStackTrace();
                        Looper.prepare();
                        Toast.makeText(Antrenamente_inregistrate_goal.this.getContext(), "A aparut o problema la incarcarea datelor", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Looper.prepare();
                    Toast.makeText(Antrenamente_inregistrate_goal.this.getContext(), r.getEroare(), Toast.LENGTH_LONG).show();
                }
            }
        };
        t.start();


        floatingActionButtonAdaugaAntrenament
                .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent i = new Intent(Antrenamente_inregistrate_goal.this.getContext(), EditareAdaugareAntrenament.class);
                                            startActivityForResult(i,code);
                                        }
                                    }

                );

        AntrenamenteRecViewAdapter adapterr = new AntrenamenteRecViewAdapter(this);
        adapterr.setListaAntrenamente(listaAntrenamente);
        antrenamenteRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        antrenamenteRecView.setAdapter(adapterr);

        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == code) {
            if (resultCode == RESULT_OK) {
                Antrenament antrenament= (Antrenament) data.getSerializableExtra("antrenament");
                listaAntrenamente.add(antrenament);
                antrenamenteRecView.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
