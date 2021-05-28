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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaugaSerieRecView extends RecyclerView.Adapter<AdaugaSerieRecView.ViewHolder> {

    private ArrayList<Repetari> repetari;
    private Context context;
    private ViewHolder holder;
    private TextWatcher t;

    public AdaugaSerieRecView(ArrayList<Repetari> repetari, Context context) {
        this.repetari = repetari;
        this.context = context;
    }

    public AdaugaSerieRecView(Context context) {
        this.context = context;
        repetari = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adauga_seria_cu_repetari, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.labelnrserie.setText((position + 1) + "");
        holder.edittextrepetati.setText(repetari.get(position).getRepetare()+"");
        if(holder.edittextrepetati.getText().toString().equals("0")){
            t = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int repetare = Integer.parseInt(holder.edittextrepetati.getText().toString());
                        repetari.get(position).setRepetare(repetare);
                    } catch (NumberFormatException ex){
                    }

                }
            };
            holder.edittextrepetati.addTextChangedListener(t);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return repetari.size();
    }

    public void addRepetare(Repetari repetare) {
        this.repetari.add(repetare);
        System.out.println(this.repetari);
        notifyDataSetChanged();


        System.out.println(this.repetari);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private EditText edittextrepetati;
        private TextView labelnrserie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            edittextrepetati = itemView.findViewById(R.id.edittextrepetati);
            labelnrserie = itemView.findViewById(R.id.labelnrserie);
        }
    }
}
