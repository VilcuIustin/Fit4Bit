package com.example.fit4bit_v002;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AfisarePozaDefaultRecView extends RecyclerView.Adapter<AfisarePozaDefaultRecView.ViewHolder> {
    AfisarePozaDefault afisarePozaDefault;
    public AfisarePozaDefaultRecView( AfisarePozaDefault afisarePozaDefault){
        this.afisarePozaDefault=afisarePozaDefault;
    }

    private ArrayList<String> listaPoze = new ArrayList<>();
    private  Comunication c;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.afisare_poze_default_model, parent, false);
        ViewHolder holder = new ViewHolder(view);
        c= new Comunication(view.getContext());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                Log.e("ceva","https://10.0.2.2:44336/"+listaPoze.get(position));
//                Bitmap b=c.loadImage("https://10.0.2.2:44336/"+listaPoze.get(position));
//                afisarePozaDefault.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.imageView.setImageBitmap(b);
//                    }
//                });
//            }
//        };
//       t.start();
    }

    @Override
    public int getItemCount() {
        return listaPoze.size();
    }

    public void setListaPoze(ArrayList<String> listaPoze) {
        this.listaPoze = listaPoze;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poza_din_model_pt_antr_default);
        }
    }
}
