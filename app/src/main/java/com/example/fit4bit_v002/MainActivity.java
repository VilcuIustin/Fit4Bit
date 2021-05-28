package com.example.fit4bit_v002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private Comunication comunication;
    private int code;
    private Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences memory = getSharedPreferences("Fit4Bit", MODE_PRIVATE);
        if (memory.contains("token")) {        //daca tokenul  a fost gasit
            setContent();
        } else {
            intent = new Intent(MainActivity.this, Welcome.class);
            startActivityForResult(intent, code);
        }

    }

    public void setContent() {
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Fit 4 Bit");

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.ViewNavigare);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragmentFit4BitScreen());
        fragmentTransaction.commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code) {
            if (resultCode == RESULT_OK) {
                System.out.println("a ajuns aici");
                setContent();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater= getMenuInflater();
//        inflater.inflate(R.menu.meniusarci, menu);
//        System.out.println("Buna ziua!");
//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        };
//        menu.findItem(R.id.sarci).setOnActionExpandListener(onActionExpandListener);
//        SearchView searchView = (SearchView) menu.findItem(R.id.sarci).getActionView();
//        searchView.setQueryHint("Coaie cauta drq");
//        return true;
//    }
//


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new MainFragmentFit4BitScreen());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.profile) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new ProfilUtilizatorRecView());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.settings_menu_drawer) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Setari());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.calculatorBmr) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new BmrCalculatorWindow());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.calculatorBmi) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new BmiCalculatorWindow());
            fragmentTransaction.commit();

        }
        if (item.getItemId() == R.id.alimentatie_drawer) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Alimente_inregistrate_goal());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.antrenamente_drawer) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Antrenamente_inregistrate_goal());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.antrenamentesugerate) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new ListaNumeAntrenamente());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.logout_drawer) {
            SharedPreferences memory = getSharedPreferences("Fit4Bit", MODE_PRIVATE);
            memory.edit().remove("token").commit();
            finish();
            startActivity(getIntent());
            Toast.makeText(this, "Ati fost delogat cu succes!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}