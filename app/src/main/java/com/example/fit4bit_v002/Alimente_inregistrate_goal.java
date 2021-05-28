package com.example.fit4bit_v002;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

//implements AdapterView.OnItemSelectedListener
public class Alimente_inregistrate_goal extends Fragment {

    private RecyclerView alimenteRecView;
    private Spinner spinner;
    private TextView nrStartBara;
    private ProgressBar progressBar;
    private Utilizator utilizator = new Utilizator();
    private TextView label_progres;
    private TextView txt_calorii_minus;
    private Comunication comunication;
    private ArrayList<Aliment> alimente;
    private ArrayList<Aliment> alimente_default;
    private float calorii;
    private FloatingActionButton adaugaAliment, schimbaRecView;
    private int code;
    private ISetezCalorii iSetezCalorii;
    private boolean schimbareLista = false;
    private AlimenteRecViewAdapter adapter7;
    private boolean isLoading = false;
    private int pagesize = 10;
    private long page = 0;
    private String product = "";
    private Thread search;

    private View view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.alimente_inregistrate_goal, container, false);
        setHasOptionsMenu(true);

        utilizator.setSex('M');
        utilizator.setData_Nastere(new Date());
        this.getActivity().setTitle("Alimente");
        iSetezCalorii = new ISetezCalorii() {
            @Override
            public float getCalorii() {
                return Alimente_inregistrate_goal.this.calorii;
            }

            @Override
            public void setCalorii(float calorii) {
                Alimente_inregistrate_goal.this.calorii = calorii;
            }
        };
        comunication = new Comunication(this.getContext());
        new Thread() {
            @Override
            public void run() {
                Raspuns u = comunication.getUtilizator();
                if (u.getStatus()) {
                    ObjectMapper o = new ObjectMapper();
                    try {
                        System.out.println(u.getRezultat());
                        utilizator = o.readValue(new JSONArray(u.getRezultat().toString()).get(0).toString(), Utilizator.class);

                    } catch (JsonProcessingException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                Raspuns r = comunication.getAlimente();
                alimente = new ArrayList<>();
                if (r.getStatus()) {
                    try {

                        ObjectMapper o = new ObjectMapper();
                        CollectionType typeReference =
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Aliment.class);
                        alimente = o.readValue(r.getRezultat().toString(), typeReference);


                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Looper.prepare();
                    Toast.makeText(Alimente_inregistrate_goal.this.getContext(), r.getEroare(), Toast.LENGTH_SHORT).show();
                }
                Alimente_inregistrate_goal.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alimenteRecView = view.findViewById(R.id.alimenteRecView);
                        progressBar = view.findViewById(R.id.progressBar);
                        spinner = view.findViewById(R.id.spinner_activ);
                        nrStartBara = view.findViewById(R.id.nr_start_bara);
                        label_progres = view.findViewById(R.id.label_progres);
                        txt_calorii_minus = view.findViewById(R.id.txt_calorii_minus);
                        adaugaAliment = view.findViewById(R.id.fabadaugaaliment);
                        schimbaRecView = view.findViewById(R.id.fabschimbarecview);
                        alimente_default = new ArrayList<>();
                        Resources res = view.getResources();
                        System.out.println(progressBar.getProgress());
                        String mesaj = res.getString(R.string.progresul_dvs, progressBar.getProgress());
                        label_progres.setText(mesaj);

                        progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#76ff03")));

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Alimente_inregistrate_goal.this.getContext(), R.array.activitate_utilizator, R.layout.color_spinner_layout);
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                        spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        adapter7 = new AlimenteRecViewAdapter(Alimente_inregistrate_goal.this, progressBar, label_progres, txt_calorii_minus, iSetezCalorii);
                        adapter7.setAlimente(alimente);
                        alimenteRecView.setLayoutManager(new LinearLayoutManager(Alimente_inregistrate_goal.this.getContext()));
                        alimenteRecView.setAdapter(adapter7);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ArrayList<Integer> listaRezultateBmr = new ArrayList<Integer>(Arrays.<Integer>asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)}));
                                if (utilizator.getInaltime() != 0 && utilizator.getMasa() != 0) {
                                    BmrCalculatorUtilizator bmrCalculatorUtilizator = new BmrCalculatorUtilizator(utilizator);
                                    listaRezultateBmr = new ArrayList<>(bmrCalculatorUtilizator.seteazaListaCuToateBmr(bmrCalculatorUtilizator.calculeazaBMR()));
                                }

                                switch (position) {
                                    case 0:
                                        nrStartBara.setText(listaRezultateBmr.get(0).toString());
                                        progressBar.setMax((listaRezultateBmr.get(0).intValue()));
                                        break;
                                    case 1:
                                        nrStartBara.setText(listaRezultateBmr.get(1).toString());
                                        progressBar.setMax((listaRezultateBmr.get(1).intValue()));
                                        break;
                                    case 2:
                                        nrStartBara.setText(listaRezultateBmr.get(2).toString());
                                        progressBar.setMax((listaRezultateBmr.get(2).intValue()));
                                        break;
                                    case 3:
                                        nrStartBara.setText(listaRezultateBmr.get(3).toString());
                                        progressBar.setMax((listaRezultateBmr.get(3).intValue()));
                                        break;
                                    case 4:
                                        nrStartBara.setText(listaRezultateBmr.get(4).toString());
                                        progressBar.setMax((listaRezultateBmr.get(4).intValue()));
                                        break;
                                }

                                if(iSetezCalorii.getCalorii()<=progressBar.getMax()){
                                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#76ff03")));
                                    txt_calorii_minus.setText(0 + "");
                                }else{
                                    txt_calorii_minus.setText(""+((int)iSetezCalorii.getCalorii()-progressBar.getMax()));
                                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff426e")));
                                }
                                progressBar.setProgress((int)iSetezCalorii.getCalorii());


//                                if (progressBar.getMax() < iSetezCalorii.getCalorii())
//                                    txt_calorii_minus.setText("" + (int) (iSetezCalorii.getCalorii() - progressBar.getMax()));
//
//                                if (progressBar.getMax() > iSetezCalorii.getCalorii()) {
//                                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#76ff03")));
//                                    txt_calorii_minus.setText(0 + "");
//                                } else {
//
//
//                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        adaugaAliment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getActivity(), AdaugaAliment.class);
                                startActivityForResult(i, code);
                            }
                        });
                        isLoading = true;
                        schimbaRecView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                schimbareLista = !schimbareLista;
                                if (schimbareLista) {
                                    page = 0;
                                    Thread t = new Thread() {
                                        @Override
                                        public void run() {
                                            Raspuns u = comunication.getAlimenteDefaultPaginated(pagesize, page, "");
                                            if (u.getStatus()) {
                                                ObjectMapper o = new ObjectMapper();
                                                CollectionType typeReference =
                                                        TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Aliment.class);

                                                try {
                                                    alimente_default = o.readValue(u.getRezultat().toString(), typeReference);
                                                    page++;

                                                    Alimente_inregistrate_goal.this.getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            adapter7.setAlimente(alimente_default);
                                                            isLoading = false;
                                                        }
                                                    });
                                                } catch (JsonProcessingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    };
                                    t.start();

                                } else {
                                    page = 0;
                                    adapter7.setAlimente(new ArrayList<>());



                                }

                            }
                        });
                        alimenteRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }

                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                                if (!isLoading) {
                                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter7.getItemCount() - 1) {
                                        Log.e("Scroll", "onScrolled: la scoll");
                                        loadMore(true);
                                        isLoading = true;

                                    }
                                }
                            }
                        });
                    }
                });

            }
        }.start();


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == code) {
            if (resultCode == RESULT_OK) {

                alimente.add((Aliment) data.getSerializableExtra("aliment"));
                alimenteRecView.getAdapter().notifyDataSetChanged();
            }
        }
    }


    private void loadMore(boolean mod) {
        Log.e("pagina", "" + page);
        search = new Thread() {

            @Override
            public void run() {

                Raspuns u;
                if (schimbareLista) {
                    u = comunication.getAlimenteDefaultPaginated(pagesize, page, product);
                } else {
                    u = comunication.getAlimentePaginated(pagesize, page, product);
                }
                if (u.getStatus()) {
                    ObjectMapper o = new ObjectMapper();
                    CollectionType typeReference =
                            TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Aliment.class);

                    try {
                        alimente_default = o.readValue(u.getRezultat().toString(), typeReference);
                        Alimente_inregistrate_goal.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mod)
                                    adapter7.addAlimente(alimente_default);
                                else
                                    adapter7.setAlimente(alimente_default);
                                page++;
                                isLoading = false;
                            }
                        });
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        search.start();


    }


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.meniusarci, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.sarci).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.sarci).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("SEARCHVIEW", searchView.getQuery().toString());
                page = 0;
                product = searchView.getQuery().toString();
                loadMore(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                page = 0;
                onQueryTextSubmit(newText);
                return true;
            }
        });

        searchView.setQueryHint("Cauta numele alimentului");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
