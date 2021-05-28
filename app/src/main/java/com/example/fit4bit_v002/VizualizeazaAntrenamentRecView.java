package com.example.fit4bit_v002;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class VizualizeazaAntrenamentRecView extends RecyclerView.Adapter<VizualizeazaAntrenamentRecView.ViewHolder> {

    private ArrayList<Exercitiu> listaExercitii = new ArrayList<>();
    private Context context;
    public VizualizeazaAntrenamentRecView(ArrayList<Exercitiu> listaExercitii, Context context) {
        this.listaExercitii = listaExercitii;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_exercitii, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e( "VIEW    : ",listaExercitii.get(position).getRepetari().toString() );
        holder.numeExercitiu.setText(listaExercitii.get(position).getDenumire());
        SeriiRepetariRecViewAdapter adapterr = new SeriiRepetariRecViewAdapter(new ArrayList<>(listaExercitii.get(position).getRepetari()));
        holder.recyclerViewSeriiRepatari.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewSeriiRepatari.setAdapter(adapterr);

    }

    @Override
    public int getItemCount() {
        return listaExercitii.size();
    }

    public void setListaExercitii(ArrayList<Exercitiu> listaExercitii) {
        this.listaExercitii = listaExercitii;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView numeExercitiu;
        private RecyclerView recyclerViewSeriiRepatari;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numeExercitiu = itemView.findViewById(R.id.nume_exercitiu);
            recyclerViewSeriiRepatari = itemView.findViewById(R.id.reciclerViewAlCarduluiExercitiului);
        }
    }
}
