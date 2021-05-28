package com.example.fit4bit_v002;

import android.content.DialogInterface;
import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlertDialogBmrCalculator {

    AlertDialogBmrCalculator(View view, double bmr) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext());
        builder.setTitle("Informatii despre rata metabolica bazala");
        BmrCalculator bmrCalculator = new BmrCalculator();

        builder.setMessage(bmrCalculator.rezultatBMR(bmr) + "\n" + bmrCalculator.calculeazaTotal(bmr));

        builder.setNeutralButton(R.string.cancel_popup_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
