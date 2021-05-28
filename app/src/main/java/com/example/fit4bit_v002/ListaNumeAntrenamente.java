package com.example.fit4bit_v002;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaNumeAntrenamente extends Fragment {
    ListView listView;
    String[] numeAntrenamente = {"Antrenamente usoare","Antrenamente moderate","Antrenamente dificile"};
    ArrayList<String> dificultate = new ArrayList(Arrays.asList("usor", "mediu", "greu"));

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_nume_antrenamente, container, false);
        this.getActivity().setTitle("Antrenamente prestabilite");

        listView = view.findViewById(R.id.lista_nume_antrenamente);
        ArrayAdapter<String> setareAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, numeAntrenamente);
        listView.setAdapter(setareAdapter);
        Comunication comunication= new Comunication(this.getContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Thread t = new Thread(){
                            @Override
                            public void run() {
                                Raspuns r=comunication.numarAntrenamente(position+1);
                                if(r.getStatus()){
                                    Intent i = new Intent(ListaNumeAntrenamente.this.getContext(), ListviewSubCategorii.class);
                                    i.putExtra("position",position+1);
                                    i.putExtra("dificultate",dificultate.get(position));
                                    i.putExtra("size",Integer.parseInt(r.getRezultat().toString()));
                                    startActivity(i);
                                }else{
                                    Looper.prepare();
                                    Toast.makeText(ListaNumeAntrenamente.this.getContext(), r.getEroare(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        t.start();

                }
            });
        return view;
    }
}
