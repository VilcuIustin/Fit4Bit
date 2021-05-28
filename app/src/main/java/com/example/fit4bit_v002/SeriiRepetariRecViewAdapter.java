package com.example.fit4bit_v002;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeriiRepetariRecViewAdapter extends RecyclerView.Adapter<SeriiRepetariRecViewAdapter.ViewHolder> {

    private ArrayList<Repetari> listaRepetari = new ArrayList<>();

    public SeriiRepetariRecViewAdapter(ArrayList<Repetari> listaRepetari) {
        this.listaRepetari = listaRepetari;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vizualizare_serii_repetari, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nrSerie.setText("Seria "+(position+1)+": ");
        holder.nrRepetari.setText(listaRepetari.get(position).getRepetare()+"");
    }

    @Override
    public int getItemCount() {
        return listaRepetari.size();
    }

    public void setListaExercitii(ArrayList<Repetari> listaExercitii) {
        this.listaRepetari = listaExercitii;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nrSerie,nrRepetari;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nrSerie = itemView.findViewById(R.id.serie_numar_recviu);
            nrRepetari = itemView.findViewById(R.id.numar_repetari_recviu);
        }
    }
}
