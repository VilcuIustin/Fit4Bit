package com.example.fit4bit_v002;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BmrCalculatorWindow extends Fragment {

    private EditText txt_masa, txt_inaltime, txt_varsta;
    private Button buton_calculeaza_bmr;
    private RadioButton checkM, checkF;

    private double masa, inaltime, bmr;
    private int varsta;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bmr_calculator_free, container, false);

        this.getActivity().setTitle("BMR calculator");



        txt_masa = view.findViewById(R.id.masa);
        txt_inaltime = view.findViewById(R.id.inaltime);
        txt_varsta = view.findViewById(R.id.txt_ns_varsta);
        checkM = view.findViewById(R.id.radio_m);
        checkF = view.findViewById(R.id.radio_f);
        buton_calculeaza_bmr = view.findViewById(R.id.buton_calculeaza_bmr);
        checkM.setChecked(true);

        buton_calculeaza_bmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    masa = Double.parseDouble(txt_masa.getText().toString());
                    inaltime = Double.parseDouble(txt_inaltime.getText().toString());
                    varsta = Integer.parseInt(txt_varsta.getText().toString());
                } catch (NumberFormatException |
                        NullPointerException exception) {
                    Toast.makeText(getContext(), "Introduceti toate datele", Toast.LENGTH_SHORT).show();
                    return;
                }


                BmrCalculator bmrCalculator = new BmrCalculator();
                if (checkM.isChecked() == true) {
                    bmr = bmrCalculator.calculeazaBMR(masa, inaltime, varsta, "M");
                    AlertDialogBmrCalculator alertDialogBmrCalculator = new AlertDialogBmrCalculator(v, bmr);
                }
                if (checkF.isChecked() == true) {
                    bmr = bmrCalculator.calculeazaBMR(masa, inaltime, varsta, "F");
                    AlertDialogBmrCalculator alertDialogBmrCalculator = new AlertDialogBmrCalculator(v, bmr);
                }


            }
        });
        return view;
    }
}
