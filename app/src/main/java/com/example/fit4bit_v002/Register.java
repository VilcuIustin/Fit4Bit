package com.example.fit4bit_v002;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity {
    private Button register, datePicker;
    private EditText date, emailReg, passwordReg, nume;
    private Comunication comunication;
    private  long data_nastere = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Register");
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LogoGigapack3));
        }
        setContentView(R.layout.register);
        comunication= new Comunication(this);
        datePicker = findViewById(R.id.DatePicker);
        register = findViewById(R.id.Create);
        date = findViewById(R.id.Date);
        RadioGroup c= findViewById(R.id.sex);
        c.check(findViewById(R.id.checkBox2).getId());
        datePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailReg = findViewById(R.id.emailReg);
                String email = String.valueOf(emailReg.getText()).trim();
                passwordReg = findViewById(R.id.passwordReg);
                String password = String.valueOf(passwordReg.getText()).trim();
                nume = findViewById(R.id.editTextTextPersonName);
                String numeUser = nume.getText().toString().trim();
                System.out.println(date.getText().toString());

                try {
                    data_nastere = ((DateFormat)new SimpleDateFormat("dd-MM-yyyy")).parse(date.getText().toString()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }

                char sex;
                RadioGroup c= findViewById(R.id.sex);
                if(findViewById(R.id.checkBox).getId()==c.getCheckedRadioButtonId())
                    sex='F';
                else
                    sex='M';


                System.out.println(email+"   "+password+"   "+ numeUser+"    "+data_nastere+"  "+ sex);
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        register.setClickable(false);
                        Raspuns r=comunication.sendRegister(email,password,numeUser,data_nastere, sex);

                        if(r.getStatus()){
                            Looper.prepare();
                            Toast.makeText(Register.this, "Contul a fost creat!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(Register.this, r.getEroare(), Toast.LENGTH_SHORT).show();
                        }
                        register.setClickable(true);

                    }
                };
                t.start();

            }
        });
    }
}
