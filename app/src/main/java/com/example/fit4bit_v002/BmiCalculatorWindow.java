package com.example.fit4bit_v002;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.regex.Pattern;

public class BmiCalculatorWindow extends Fragment {

    private EditText txt_masa, txt_inaltime;
    private TextView rezultat_bmi_1, rezultat_bmi_2;
    private Button buton_calculeaza_bmi;

    private double masa, inaltime, bmi;


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            int length = text.length();

            if (length > 0 && !Pattern.matches("[1-9]{0,3}\\.*[0-9]", text)) {
                s.delete(length - 1, length);
            }
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bmi_calculator_free, container, false);

        this.getActivity().setTitle("BMI Calculator");

        txt_masa = view.findViewById(R.id.txt_nd_masa);
        txt_masa.addTextChangedListener(textWatcher);
        txt_inaltime = view.findViewById(R.id.txt_nd_inaltime);

        rezultat_bmi_1 = view.findViewById(R.id.rezultat_bmi_1);
        rezultat_bmi_2 = view.findViewById(R.id.rezultat_bmi_2);

        buton_calculeaza_bmi = view.findViewById(R.id.buton_calculeaza_bmi);


        buton_calculeaza_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmiCalculator BMI = new BmiCalculator();
                try {
                    masa = Double.parseDouble(txt_masa.getText().toString());
                    inaltime = Double.parseDouble(txt_inaltime.getText().toString());
                    bmi = BMI.calculeazaBMI(masa, inaltime);
                } catch (
                        NullPointerException | NumberFormatException exception
                ) {
                    Toast.makeText(getContext(), "Introduceti toate datele", Toast.LENGTH_SHORT).show();
                    return;
                }

                rezultat_bmi_1.setText("" + BMI.calculeazaBMI(masa, inaltime));
                rezultat_bmi_2.setText("" + BMI.rezultatBMI(BMI.calculeazaBMI(masa, inaltime)));

                AlertDialogBmiCalculator alertDialogBmiCalculator = new AlertDialogBmiCalculator(v, bmi);
            }
        });
        return view;
    }
}
