package com.example.fit4bit_v002;

import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AntrenamenteRecViewAdapter extends RecyclerView.Adapter<AntrenamenteRecViewAdapter.ViewHolder> {

    private ArrayList<Antrenament> listaAntrenamente = new ArrayList<>();
    private Antrenamente_inregistrate_goal antrenamenteGoal;
    private Comunication comunication;

    public AntrenamenteRecViewAdapter(Antrenamente_inregistrate_goal antrenamenteGoal) {
        this.antrenamenteGoal = antrenamenteGoal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_antrenamente, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numeAntr.setText(listaAntrenamente.get(position).getDenumire());
        holder.durataAntr.setText("Durata: "+(int)(listaAntrenamente.get(position).getDurata()) + " de minute");

        holder.vizAntr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comunication comunication= new Comunication(AntrenamenteRecViewAdapter.this.antrenamenteGoal.getContext());
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        Raspuns r=comunication.getAntrenamentFull(listaAntrenamente.get(position).getId());
                        if(r.getStatus()){

                            ObjectMapper o = new ObjectMapper();
                            try {
                                JSONObject j=(JSONObject)r.getRezultat();

                                Antrenament a =o.readValue( j.getString("antrenament"),Antrenament.class);
                                System.out.println(a);
                                Intent intent = new Intent(antrenamenteGoal.getActivity(), VizualizareAntrenament.class);
                                intent.putExtra("NumeEx",a.getDenumire());
                                intent.putExtra("Antrenament", a);
                                antrenamenteGoal.startActivity(intent);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//
                        }else{
                            Looper.prepare();
                            Toast.makeText(antrenamenteGoal.getContext(), r.getEroare(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                t.start();



            }
        });
        holder.stergeAntr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        comunication= new Comunication(antrenamenteGoal.getContext());
                        Raspuns r= comunication.stergeAntrenament(listaAntrenamente.get(position).getId());
                        if(r.getStatus()){
                            listaAntrenamente.remove(position);
                            JSONObject j = (JSONObject) r.getRezultat();
                            Looper.prepare();
                            try {
                                Toast.makeText(AntrenamenteRecViewAdapter.this.antrenamenteGoal.getContext(), j.get("message").toString(), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            String eroare="";
                            try {
                                eroare= new JSONObject(r.getEroare()).getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                eroare=r.getEroare();
                            }

                            Looper.prepare();
                            Toast.makeText(AntrenamenteRecViewAdapter.this.antrenamenteGoal.getContext(), eroare, Toast.LENGTH_SHORT).show();
                        }
                        AntrenamenteRecViewAdapter.this.antrenamenteGoal.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        });


                    }
                };
                t.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAntrenamente.size();
    }

    public void setListaAntrenamente(ArrayList<Antrenament> listaAntrenamente) {
        this.listaAntrenamente = listaAntrenamente;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView numeAntr, durataAntr;
        private RelativeLayout parinte;
        private Button stergeAntr, vizAntr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numeAntr = itemView.findViewById(R.id.nume_antrenament);
            durataAntr = itemView.findViewById(R.id.durata_antrenament);
            parinte = itemView.findViewById(R.id.parinteLA2);
            stergeAntr = itemView.findViewById(R.id.buton_sterge_antr);
            vizAntr = itemView.findViewById(R.id.buton_viz_antr);
        }
    }
}
