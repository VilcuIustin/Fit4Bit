package com.example.fit4bit_v002;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


public class ProfilUtilizatorRecView extends Fragment {


    private RecyclerView profilRecView;
    private Utilizator utilizator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profil_utilizator_recview, container, false);
        Comunication comunication = new Comunication(ProfilUtilizatorRecView.this.getContext());
        this.getActivity().setTitle("Profil");
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Raspuns r = comunication.getUtilizator();
                Looper.prepare();
                if (r.getStatus()) {
                    ObjectMapper o = new ObjectMapper();
                    try {
                        System.out.println(r.getRezultat());
                        utilizator = o.readValue(new JSONArray(r.getRezultat().toString()).get(0).toString(), Utilizator.class);
                        profilRecView = view.findViewById(R.id.recViewCuCarduriProfil);
                        ArrayList<ItemPageView> pageViewArrayList = new ArrayList<>();
                        // aici in loc de drawable pozafir4bit o sa fie poza lui de profil
                        pageViewArrayList.add(new ItemPageView(R.drawable.welcome, "Bine ati venit " + utilizator.getNume() + "!" + "\nVa multumim ca folositi aplicatia noastra!", "Mai jos o sa gasiti datele profilului dumneavoastra!"));
                        pageViewArrayList.add(new ItemPageView(R.drawable.profileicon, "Informatii despre profil", "Varsta inregistrata de dvs. este: " + (new java.util.Date().getYear() -utilizator.getData_Nastere().getYear()) + " de ani" + "."
                                + "\n" + "Sexul inregistrat de dvs. este: " + utilizator.getSex() + "." + "\n" + "Inaltimea dvs. este: " + utilizator.getInaltime() + "." + "\n" + "Masa dvs. este: " + utilizator.getMasa() + "."));
                        pageViewArrayList.add(new ItemPageView(R.drawable.unu, "Singurele notiuni teoretice din fitness pe care ar trebui sa le intelegeti", "1.Calories in calories out!" + "Legile termodinamicii nu mint!" + "\n" + "Daca consumati mai multe calorii decat " +
                                "ardeti intr-o zi, atunci va aflati intr-un surplus caloric deci o sa va ingrasati!" + "\n" + "Daca consumati mai putine calorii decat ardeti intr-o zi, atunci va aflati intr-un deficit caloric deci o sa slabiti!" +
                                "\n\n" + "2.Cel mai important muschi din corpul uman nu este bicepsul!" + "\n" + "Departamentul de Sănătate și Servicii Umane din SUA recomandă cel puțin 150 de minute pe săptămână de activitate fizică de intensitate moderată - gândiți-vă la asta ca la 30 de minute, cinci zile pe săptămână - pentru toți adulții, chiar și persoanele în vârstă și cu dizabilități. Nu uitati, inima este cel mai important muschi, aveti grija de ea!\n\n" + "" +
                                "3.Muschii nu au cum sa vada ce exercitii faceti,tot ce conteaza este tensiunea musculara si distanta de miscare pe care o realizati, intindeti-va muschii cat mai mult in timpul miscarilor de bodybuilding, ATENTIE, in parametri optimi, cu o executie cat mai buna."));

                        ProfilUtilizatorRecView.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                ProfilUtilizatorRecViewAdapter adapter = new ProfilUtilizatorRecViewAdapter(getContext());
                                adapter.setItemPageViewArrayList(pageViewArrayList);

                                profilRecView.setAdapter(adapter);
                                profilRecView.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                        });



                        System.out.println(utilizator);
                    } catch (JsonProcessingException e) {
                        System.out.println(e.getMessage());
                        Toast.makeText(ProfilUtilizatorRecView.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("PROBLEMA", "run: " + r.getEroare());
                }
            }
        }.start();

        return view;
    }
}
