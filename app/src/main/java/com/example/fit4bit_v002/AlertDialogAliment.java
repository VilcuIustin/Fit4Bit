package com.example.fit4bit_v002;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.function.Predicate;

public class AlertDialogAliment {


    private int diferenta;


    AlertDialogAliment(View view, ProgressBar progressBar, int calorii, TextView label_progres, TextView txt_calorii_minus, long id, ArrayList<Aliment> alimente, AlimenteRecViewAdapter  adapter, ISetezCalorii iSetezCalorii) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext());
        builder.setTitle("Informatii aliment");
        builder.setMessage("Mai multe informatii");

        builder.setPositiveButton(R.string.adauga_calorii_aliment, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(progressBar.getProgress()+calorii<=progressBar.getMax()){
                    iSetezCalorii.setCalorii(iSetezCalorii.getCalorii()+calorii);
                    progressBar.setProgress((int) (iSetezCalorii.getCalorii()));
                }else{
                    iSetezCalorii.setCalorii(iSetezCalorii.getCalorii()+calorii);
                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff426e")));
                    progressBar.setProgress((int) (iSetezCalorii.getCalorii()+calorii));
                    diferenta=(int)iSetezCalorii.getCalorii()-progressBar.getMax();
                    txt_calorii_minus.setText(diferenta+"");
                }
                Resources res = view.getResources();
                String mesaj = res.getString(R.string.progresul_dvs, (int) iSetezCalorii.getCalorii());
                label_progres.setText(mesaj);

//                iSetezCalorii.setCalorii(iSetezCalorii.getCalorii()+calorii);
//                Log.e("ceva", "onClick: "+progressBar.getMax() );
//                if (iSetezCalorii.getCalorii()  > progressBar.getMax()) {
//                    diferenta = (int)(iSetezCalorii.getCalorii())-progressBar.getMax();
//                    System.out.println(diferenta);
//                    txt_calorii_minus.setText(diferenta+"");
//
//                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff426e")));
//
//                }
//                    progressBar.incrementProgressBy(calorii);
//                    Resources res = view.getResources();
//                    String mesaj = res.getString(R.string.progresul_dvs, (int)iSetezCalorii.getCalorii());
//                    label_progres.setText(mesaj);

            }
        });
        builder.setNegativeButton(R.string.sterge_aliment, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Comunication comunication= new Comunication(view.getContext());
                Thread t = new Thread(){
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        Raspuns r =comunication.stergeAliment(id);
                        Looper.prepare();
                        if(r.getStatus()){
                            Toast.makeText(view.getContext(), "Aliment sters", Toast.LENGTH_SHORT).show();
                            alimente.removeIf(new Predicate<Aliment>() {
                                @Override
                                public boolean test(Aliment aliment) {
                                    return (aliment.getId()==id);
                                }
                            });
                            adapter.getParinte().getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });


                        }
                        else
                            Toast.makeText(view.getContext(), "A aparut o problema", Toast.LENGTH_SHORT).show();
                        this.interrupt();
                    }
                };
                t.start();
            }
        });
        builder.setNeutralButton(R.string.cancel_popup_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface  dialog, int which) {

            }
        });

        builder.show();

    }

}