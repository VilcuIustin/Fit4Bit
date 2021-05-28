package com.example.fit4bit_v002;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class ModificaUtilizator extends AppCompatActivity {
    private Utilizator utilizator;
    private EditText nume, masa, inaltime, date;
    private Button datePicker, save;
    private char sex;
    TextWatcher textWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ((s.toString().length() == 1 &&( s.toString().startsWith("0")) ||  s.toString().startsWith("."))){
                s.clear();
            }
        }
    };

    TextWatcher textWatcherInaltime = new TextWatcher() {
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
            if (length > 0 && !Pattern.matches("[1-2]\\.*[0-9]{0,2}", text)) {
                s.delete(length - 1, length);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner);
        Comunication comunication = new Comunication(ModificaUtilizator.this);

        Thread t = new Thread() {
            @Override
            public void run() {
                Raspuns r = comunication.getUtilizator();
                Looper.prepare();
                if (r.getStatus()) {
                    ObjectMapper o = new ObjectMapper();
                    try {
                        System.out.println(r.getRezultat());
                        utilizator = o.readValue(new JSONArray(r.getRezultat().toString()).get(0).toString(), Utilizator.class);
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                setContentView(R.layout.modifica_profil);
                                nume = findViewById(R.id.nume_schimbare);
                                nume.setText(utilizator.getNume());
                                masa = findViewById(R.id.alegeti_masa_schimbare);
                                masa.setText(utilizator.getMasa() + "");

                                masa.addTextChangedListener(textWatcher);




                                inaltime = findViewById(R.id.inaltime_schimbare);
                                inaltime.setText(utilizator.getInaltime() + "");
                                inaltime.addTextChangedListener(textWatcherInaltime);
                                datePicker = findViewById(R.id.datepicker_schimbare);
                                RadioGroup c = findViewById(R.id.sex_modifica);
                                if (utilizator.getSex() == 'M')
                                    c.check(findViewById(R.id.cb_M_schimbare).getId());
                                else
                                    c.check(findViewById(R.id.cb_F_schimbare).getId());

                                date = findViewById(R.id.data_schimbare);
                                save = findViewById(R.id.buton_schimba_detalii_cont);

                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                date.setText(df.format(utilizator.getData_Nastere()));
                                datePicker.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                    @Override
                                    public void onClick(View v) {
                                        int mYear, mMonth, mDay;
                                        final Calendar c = Calendar.getInstance();
                                        mYear = c.get(Calendar.YEAR);
                                        mMonth = c.get(Calendar.MONTH);
                                        mDay = c.get(Calendar.DAY_OF_MONTH);
                                        DatePickerDialog datePickerDialog = new DatePickerDialog(ModificaUtilizator.this, new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                                            }
                                        }, mYear, mMonth, mDay);
                                        Date today = new Date();
                                        Calendar d = Calendar.getInstance();
                                        d.setTime(today);
                                        d.add(Calendar.YEAR, -120);

                                        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                                        datePickerDialog.getDatePicker().setMinDate(d.getTime().getTime());
                                        datePickerDialog.show();
                                    }
                                });
                                synchronized (this) {
                                    this.notify();
                                }

                            }
                        };
                        synchronized (runnable) {
                            ModificaUtilizator.this.runOnUiThread(runnable);
                            runnable.wait();
                        }


                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Thread tread = new Thread() {
                                    @Override
                                    public void run() {
                                        Utilizator u = new Utilizator();
                                        RadioGroup c = findViewById(R.id.sex_modifica);
                                        if (findViewById(R.id.cb_M_schimbare).getId() == c.getCheckedRadioButtonId())
                                            sex = 'M';
                                        else
                                            sex = 'F';
                                        u.setSex(sex);
                                        Looper.prepare();
                                        try {
                                            u.setData_Nastere(((DateFormat) new SimpleDateFormat("dd-MM-yyyy")).parse(date.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            Toast.makeText(ModificaUtilizator.this, "A aparut o problema la data de nastere", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        try{
                                            u.setMasa(Double.parseDouble(String.valueOf(masa.getText())));
                                        }catch (NumberFormatException e){
                                            Toast.makeText(ModificaUtilizator.this, "Masa este obligatorie", Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                        try {
                                            u.setData_Nastere(((DateFormat) new SimpleDateFormat("dd-MM-yyyy")).parse(date.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            Toast.makeText(ModificaUtilizator.this, "A aparut o eroare la modificarea varstei", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        try{
                                            u.setInaltime(Double.parseDouble(inaltime.getText().toString()));
                                        }catch(NumberFormatException e){
                                            Toast.makeText(ModificaUtilizator.this, "Inaltimea este obligatorie", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        u.setNume(nume.getText().toString().trim());
                                        Raspuns r = comunication.updateUtilizator(u);

                                        if (r.getStatus()) {
                                            Toast.makeText(ModificaUtilizator.this, "Datele au fost actualizate", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ModificaUtilizator.this, r.getEroare(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                tread.start();

                            }
                        });

                    } catch (JsonProcessingException | JSONException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ModificaUtilizator.this, "A aparut o problema!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        t.start();
    }




}
