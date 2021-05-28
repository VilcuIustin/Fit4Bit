package com.example.fit4bit_v002;

import android.content.DialogInterface;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlertDialogBmiCalculator {
    AlertDialogBmiCalculator(View view, double bmi) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext());
        builder.setTitle("Informatii despre rata metabolica bazala");
        BmiCalculator bmiCalculator = new BmiCalculator();

        builder.setMessage(bmiCalculator.toString() + "\n" + "\n                 " + bmiCalculator.rezultatBMI(bmi));

        builder.setNeutralButton(R.string.cancel_popup_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
