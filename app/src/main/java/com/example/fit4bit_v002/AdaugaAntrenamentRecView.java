package com.example.fit4bit_v002;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdaugaAntrenamentRecView extends RecyclerView.Adapter<AdaugaAntrenamentRecView.ViewHolder> {

    private ArrayList<Exercitiu> exercitii;
    private Context context;

    public ArrayList<Exercitiu> getExercitii() {
        return exercitii;
    }

    public AdaugaAntrenamentRecView(ArrayList<Exercitiu> exercitii, Context context) {
        this.exercitii = exercitii;
        this.context = context;
    }


    public AdaugaAntrenamentRecView(Context context) {
        this.context = context;
        exercitii = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adauga_serii_repetari, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.exercitii.get(position).getRepetari() == null)
            this.exercitii.get(position).setRepetari(new ArrayList<>());
        AdaugaSerieRecView adapterr = new AdaugaSerieRecView((ArrayList<Repetari>) this.exercitii.get(position).getRepetari(), context);
        holder.recview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recview.setAdapter(adapterr);

        holder.numeExercitiu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String repetare = holder.numeExercitiu.getText().toString();
                exercitii.get(position).setDenumire(repetare);
            }
        });

        holder.adaugaSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repetari r = new Repetari();
                adapterr.addRepetare(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercitii.size();
    }

    public void addExercitiu(Exercitiu exercitiu) {
        this.exercitii.add(exercitiu);
        notifyItemChanged(getItemCount() - 1);
        System.out.println(this.exercitii);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private Button adaugaSerie;
        private EditText numeExercitiu;
        private RecyclerView recview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adaugaSerie = itemView.findViewById(R.id.butonadaugaserie);
            numeExercitiu = itemView.findViewById(R.id.edittetnumeexercitiu);
            recview = itemView.findViewById(R.id.reciclerviewcuseriirepetari);

        }
    }
}


