package com.example.fit4bit_v002;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import static android.content.Context.MODE_PRIVATE;

public class Comunication {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");    // aici setam ce tip de informatii trimitem catre backend
    /*                   Inceput zona acceptare toate certificatele ssl                        */
    private static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    private static final SSLContext trustAllSslContext;
    private static SSLSocketFactory trustAllSslSocketFactory;

    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    /*                  Sfarsit zona acceptare toate certificatele ssl                         */
    private final String baseUrl = "https://10.0.2.2:44336/api/";
    private final OkHttpClient client;
    private final Context context;

    public Comunication(Context context) {
        this.context = context;
        trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();
        OkHttpClient client = new OkHttpClient();
        client.setSslSocketFactory(trustAllSslSocketFactory);
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        this.client = client;

    }

    public <T> Object parseData(JSONObject data, Class type) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Object value = mapper.readValue(data.toString(), type);
        Log.e("YEEEEE ", "" + value.toString());
        return value;
    }

    public Raspuns sendLogin(String email, String password) {
        return login(email, password);
    }

    private Raspuns login(String email, String password) {
        Raspuns raspuns = new Raspuns();
        JSONObject j = new JSONObject();
        try {
            j.put("Email", email);
            j.put("Password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
            return raspuns;
        }
        Log.e("Test ", j.toString());

        Request req = creareConxiune("account/login", j);
        try {
            JSONObject json = null;
            Response response = client.newCall(req).execute();


            String rezultat = response.body().string();
//                Utilizator user = (Utilizator) parseData(json, Utilizator.class);
            if (response.isSuccessful()) {

                json = new JSONObject(rezultat);
                SharedPreferences memory = context.getSharedPreferences("Fit4Bit", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = memory.edit();
                if (memory.contains("token")) {
                    myEdit.remove("token");
                }
                raspuns.setStatus(true);

                myEdit.putString("token", json.getString("token"));
                myEdit.commit();
            } else {
                raspuns.setEroare(rezultat);
                raspuns.setStatus(false);
            }
//                raspuns.setRezultat(user);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns sendRegister(String email, String password, String nume, long data_nastere, char sex) {
        return register(email, password, nume, data_nastere, sex);
    }

    private Raspuns register(String email, String password, String nume, long data_nastere, char sex) {
        JSONObject j = new JSONObject();
        Raspuns raspuns = new Raspuns();
        //Log.e("Data", data_nastere + "");
        try {
            j.put("email", email);
            j.put("password", password);
            j.put("nume", nume);
            j.put("Data_Nastere", data_nastere);
            j.put("Sex", sex);
        } catch (JSONException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
            return raspuns;
        }

        Request req = creareConxiune("account/register", j);
        Response response = null;
        String ras;
        try {
            response = client.newCall(req).execute();
            ras = response.body().string();
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(ras);
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare("A aparut o problema la conexiunea cu serverul");
        }
        return raspuns;
    }

    public Raspuns sendAdaugaAntrenament(Antrenament antrenament) {
        return adaugaAntrenament(antrenament);
    }

    private Raspuns adaugaAntrenament(Antrenament antrenament) {
        JSONObject responseObj;

        Raspuns raspuns = new Raspuns();
        //   ArrayList<Repetari> repetar= new ArrayList<Repetari>(Arrays.asList(new Repetari(1), new Repetari(2),new Repetari(3) ,new Repetari(4) ));
        //  a.add(new Exercitiu("Exercitiu adaugat de pe android ", 4, repetar));
        //  antrenament = new Antrenament("Exercitiu", a, 40);
        try {
            JSONObject j = new JSONObject();
            j.put("denumire", antrenament.getDenumire());
            j.put("duarta", antrenament.getDurata());

            Pair<JSONArray, String> exercitiiJson = parseExercitii(antrenament.getExercitii());
            if (exercitiiJson.first == null) {
                raspuns.setEroare(exercitiiJson.second);
                return raspuns;
            }
            j.put("Exercitii", exercitiiJson.first);
            Log.e("json ", j.toString());
            Request req = creareConxiune("exercitii/AddAntrenament", j);
            Response response = client.newCall(req).execute();
            responseObj = new JSONObject(response.body().string());
            if (response.isSuccessful())
                raspuns.setStatus(true);

            raspuns.setRezultat(responseObj);
            parseData(responseObj, Antrenament.class);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns getAlimenteDefaultPaginated(long pagesize, long pagenumber, String product) {
        Raspuns raspuns = new Raspuns();
        Request req = creareConexiune("alimente/getAlimenteDefault?page="+pagenumber+"&pagesize="+pagesize+"&product="+product);
        try {
            Response response = client.newCall(req).execute();
            if (response.isSuccessful()) {
                raspuns.setRezultat(response.body().string());
                Log.e("TEST   ", raspuns.getRezultat().toString());
                raspuns.setStatus(true);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns getAlimentePaginated(long pagesize, long pagenumber, String product) {
        Raspuns raspuns = new Raspuns();
        Request req = creareConexiune("alimente/getAlimente?page="+pagenumber+"&pagesize="+pagesize+"&product="+product);
        try {
            Response response = client.newCall(req).execute();
            if (response.isSuccessful()) {
                raspuns.setRezultat(response.body().string());
                Log.e("TEST   ", raspuns.getRezultat().toString());
                raspuns.setStatus(true);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }


    private Pair<JSONArray, String> parseExercitii(List<Exercitiu> exercitii) {
        JSONArray rezultat = new JSONArray();

        for (Exercitiu exercitiu : exercitii) {
            JSONObject j = new JSONObject();
            try {
                j.put("denumire", exercitiu.getDenumire());
                j.put("serii", exercitiu.getSerii());
                JSONArray rezultatRepetari = new JSONArray();

                for (Repetari i : exercitiu.getRepetari()) {
                    JSONObject repetariJson = new JSONObject();
                    repetariJson.put("repetareSerie", i.getRepetare());
                    rezultatRepetari.put(repetariJson);
                }

                j.put("repetari", rezultatRepetari);
                rezultat.put(j);
            } catch (JSONException e) {
                e.printStackTrace();
                return new Pair<>(null, e.getMessage());
            }
        }
        return new Pair<>(rezultat, null);
    }


    public Raspuns getAntrenamente() {
        Raspuns raspuns = new Raspuns();
        Request req = creareConexiune("exercitii/getAntrenamenteFaraPaginare");
        try {
            Response response = client.newCall(req).execute();
            if (response.isSuccessful()) {
                JSONArray json = new JSONArray(response.body().string());
                Log.e("TEST   ", json.toString());
                raspuns.setStatus(true);
                raspuns.setRezultat(json);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(response.body().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns getAlimente() {
        Raspuns raspuns = new Raspuns();
        Request req = creareConexiune("alimente/getAlimenteFaraPaginare");
        try {
            Response response = client.newCall(req).execute();
            if (response.isSuccessful()) {
                raspuns.setRezultat(response.body().string());
                Log.e("TEST   ", raspuns.getRezultat().toString());
                raspuns.setStatus(true);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns getUtilizator() {
        Raspuns raspuns = new Raspuns();
        Request req = creareConexiune("user/GetProfile");
        Response response = null;
        try {
            response = client.newCall(req).execute();
            String res = response.body().string();
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(res);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setStatus(false);
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }


    public Raspuns getAntrenamentFull(long id) {
        Raspuns raspuns = new Raspuns();
        Request req = creareConexiune("exercitii/getAntrenament?antrenamnetId=" + id);
        try {
            Response response = client.newCall(req).execute();
            String res = response.body().string();
            JSONObject j = new JSONObject(res);
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(j);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(res);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns stergeAntrenament(long antrenamentId) {
        Raspuns raspuns = new Raspuns();

        Request req = creareConxiune("exercitii/stergeAntrenament?antrenamentId=" + antrenamentId, 3);
        try {
            Response response = client.newCall(req).execute();
            String res = response.body().string();
            JSONObject j = new JSONObject(res);
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(j);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(res);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns stergeAliment(long alimentId) {
        Raspuns raspuns = new Raspuns();

        Request req = creareConxiune("alimente/stergeAliment?alimentId=" + alimentId, 3);
        try {
            Response response = client.newCall(req).execute();
            String res = response.body().string();
            Log.e("ddd", "stergeAliment: " + res);

            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(res);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare(e.getMessage());
        }
        return raspuns;
    }

    public Raspuns adaugaAliment(Aliment aliment) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Raspuns raspuns = new Raspuns();
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(aliment);
            RequestBody payload = RequestBody.create(JSON, jsonString);
            Request req = creareConxiune("alimente/AdaugaAliment", payload);
            Response response = client.newCall(req).execute();
            String res = response.body().string();
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(res);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(res);
            }

        } catch (IOException e) {
            raspuns.setStatus(false);
            raspuns.setEroare(e.getMessage());
            e.printStackTrace();
        }
        return raspuns;
    }

    public Raspuns numarAntrenamente(int folder){
        Request req = creareConexiune("exercitii/getSubcategorieDefault?folder="+folder);
        return procesareRequest(req);
    }
    public Raspuns listaExercitii(int folder, int subfolder){
        Request req = creareConexiune("exercitii/getAntrenamenteDefault?folder="+folder+"&subfolder="+subfolder);
        return procesareRequest(req);
    }

    private Raspuns procesareRequest(Request req){
        Raspuns raspuns = new Raspuns();
        try {
            Response response = client.newCall(req).execute();
            String res = response.body().string();
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(res);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(res);
            }


        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare("Nu a putut fi realizata conexiunea!");
            raspuns.setStatus(false);
        }
        return raspuns;
    }

    public Bitmap loadImage(String path){
        Request request = new Request.Builder()
                .url(path)
                .get().build();
        try {
            Response response=client.newCall(request).execute();
            InputStream is = response.body().byteStream();
            return BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Raspuns updateUtilizator(Utilizator u) {
        ObjectMapper mapper = new ObjectMapper();
        Raspuns raspuns = new Raspuns();
        String jsonString = null;
        RequestBody payload;
        try {
            jsonString = mapper.writeValueAsString(u);
            payload = RequestBody.create(JSON, jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            raspuns.setEroare("A aparut o eroare la transmiterea utilizatorului");
            raspuns.setStatus(false);
            return raspuns;
        }
        Request req = creareConxiune("user/ActualizareUser", payload);
        try {
            Response response = client.newCall(req).execute();
            String rezultat = response.body().string();
            if (response.isSuccessful()) {
                raspuns.setStatus(true);
                raspuns.setRezultat(rezultat);
            } else {
                raspuns.setStatus(false);
                raspuns.setEroare(rezultat);
            }
        } catch (IOException e) {
            e.printStackTrace();
            raspuns.setEroare("Nu a putut fi realizata conexiunea!");
            raspuns.setStatus(false);
        }
        return raspuns;
    }

    private Request creareConxiune(String path, int tip) {                  //1 post 2 get 3 delete
        SharedPreferences memory = context.getSharedPreferences("Fit4Bit", MODE_PRIVATE);
        Request.Builder request = new Request.Builder()
                .url(baseUrl + path);
        switch (tip) {
            case 3:
                request.delete();
                break;
            default:
                request.get();
                break;
        }
        if (!memory.contains("token"))
            return request.build();
        return request.addHeader("Authorization", "Bearer " + memory.getString("token", null)).build();

    }

    private Request creareConxiune(String path, RequestBody payload) {
        SharedPreferences memory = context.getSharedPreferences("Fit4Bit", MODE_PRIVATE);
        Request.Builder request = new Request.Builder()
                .url(baseUrl + path)
                .post(payload);
        if (!memory.contains("token"))
            return request.build();
        return request.addHeader("Authorization", "Bearer " + memory.getString("token", null)).build();

    }

    private Request creareConxiune(String path, JSONObject payload) {
        RequestBody r = RequestBody.create(JSON, payload.toString());
        SharedPreferences memory = context.getSharedPreferences("Fit4Bit", MODE_PRIVATE);
        Request.Builder request = new Request.Builder()
                .url(baseUrl + path)
                .post(r);
        if (!memory.contains("token"))
            return request.build();
        return request.addHeader("Authorization", "Bearer " + memory.getString("token", null)).build();

    }

    private Request creareConexiune(String path) {
        Request.Builder request = new Request.Builder()
                .url(baseUrl + path);
        SharedPreferences memory = context.getSharedPreferences("Fit4Bit", MODE_PRIVATE);
        if (memory.getString("token", null).equals(null))
            return request.build();
        return request.addHeader("Authorization", "Bearer " + memory.getString("token", null)).build();

    }

    private URL creareUrl(String path, @Nullable Pair<String, String> query) {     // Inca nu este implementata
        return null;
    }
}