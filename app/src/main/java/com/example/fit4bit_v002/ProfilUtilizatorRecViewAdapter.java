package com.example.fit4bit_v002;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfilUtilizatorRecViewAdapter extends RecyclerView.Adapter<ProfilUtilizatorRecViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ItemPageView> itemPageViewArrayList = new ArrayList<>();

    public ProfilUtilizatorRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profil_utilizator_recview_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titlu.setText(itemPageViewArrayList.get(position).getTitle());
        holder.content.setText(itemPageViewArrayList.get(position).getDescription());
        holder.imagine.setImageResource(itemPageViewArrayList.get(position).getImage());

    }

    public void setItemPageViewArrayList(ArrayList<ItemPageView> carduri){
        this.itemPageViewArrayList=carduri;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemPageViewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagine;
        private TextView titlu,content;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagine = itemView.findViewById(R.id.imagineCardProfil);
            titlu = itemView.findViewById(R.id.titluCardProfil);
            content = itemView.findViewById(R.id.contentCardProfil);
            parent = itemView.findViewById(R.id.cardViewUtilizator);
        }
    }
}
