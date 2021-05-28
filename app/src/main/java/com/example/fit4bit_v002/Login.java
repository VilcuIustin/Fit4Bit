package com.example.fit4bit_v002;


import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {
    private EditText email, password;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.setTitle("Login");
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.LogoGigapack3));
        }
        email=findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        Button submit= findViewById(R.id.login);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comunication com = new Comunication(Login.this);
                Thread t = new Thread() {

                    @Override
                    public void run() {
                        submit.setClickable(false);


                        Raspuns r = com.sendLogin(email.getText().toString(), password.getText().toString());
                        Looper.prepare();
                        if(r.getStatus()){
                            Toast.makeText(Login.this, "Login reusit!", Toast.LENGTH_SHORT).show();
                            Login.this.setResult(RESULT_OK);
                            Login.this.finish();
                        }
                        else{
                            try {
                                Toast.makeText(Login.this, new JSONObject(r.getEroare().toString()).get("message").toString(), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(Login.this, "A aparut o problema la login", Toast.LENGTH_SHORT).show();
                            }
                        }
                        submit.setClickable(true);
                    }

                };
                t.start();
            }
        });


    }
}
