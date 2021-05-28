package com.example.fit4bit_v002;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlimenteRecViewAdapter extends RecyclerView.Adapter<AlimenteRecViewAdapter.ViewHolder> {


    private ArrayList<Aliment> alimente = new ArrayList<>();
    private int id = 0;
    private ProgressBar progressBar;
    private TextView label_progres;
    private TextView txt_calorii_minus;
    private Alimente_inregistrate_goal parinte;
    private ISetezCalorii iSetezCalorii;

    public Alimente_inregistrate_goal getParinte() {
        return parinte;
    }

    public void setParinte(Alimente_inregistrate_goal parinte) {
        this.parinte = parinte;
    }

    public AlimenteRecViewAdapter(Alimente_inregistrate_goal a, ProgressBar progressBar, TextView label_progres, TextView txt_calorii_minus,ISetezCalorii iSetezCalorii) {
        this.progressBar = progressBar;
        this.label_progres = label_progres;
        this.txt_calorii_minus = txt_calorii_minus;
        this.parinte=a;
        this.iSetezCalorii=iSetezCalorii;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_alimente, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNumeAliment.setText(alimente.get(position).toString());
        holder.detaliiAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogAliment popup = new AlertDialogAliment(v, progressBar, alimente.get(position).getCalorii(), label_progres,txt_calorii_minus,alimente.get(position).getId(),alimente,AlimenteRecViewAdapter.this, iSetezCalorii );
            }
        });
        holder.parinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return alimente.size();
    }

    public void setAlimente(ArrayList<Aliment> alimente) {
        this.alimente = alimente;
        notifyDataSetChanged();
    }
    public void addAlimente(ArrayList<Aliment> alimente){
        this.alimente.addAll(alimente);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNumeAliment;
        private RelativeLayout parinte;
        private Button detaliiAliment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumeAliment = itemView.findViewById(R.id.nume_aliment);
            parinte = itemView.findViewById(R.id.parinteLA);
            detaliiAliment = itemView.findViewById(R.id.detaliiAliment);
        }
    }
}
