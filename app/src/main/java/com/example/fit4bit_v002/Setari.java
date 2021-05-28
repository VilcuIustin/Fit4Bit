package com.example.fit4bit_v002;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;


public class Setari extends Fragment {

    ListView listView;
    String[] numeSetari = {"Schimba detaliile utilizatorului"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setari, container, false);
        this.getActivity().setTitle("SETARI");

        listView = view.findViewById(R.id.lista_nume_setari);
        ArrayAdapter<String> setareAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, numeSetari);
        listView.setAdapter(setareAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(Setari.this.getContext(),ModificaUtilizator.class);
                        startActivity(i);
                        break;
                }
            }
        });
        return view;
    }

}
