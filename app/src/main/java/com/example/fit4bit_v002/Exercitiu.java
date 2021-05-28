package com.example.fit4bit_v002;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exercitiu implements Serializable {
    private String denumire;
    private int serii;
    private ArrayList<Repetari> repetari;

    public Exercitiu(){}

    public Exercitiu(String denumire, int serie) {
        this.denumire=denumire;
        this.serii =serie;
        this.repetari=new ArrayList<Repetari>();
    }
    public Exercitiu(String denumire, int serie, ArrayList<Repetari> repetari){
        this.denumire= denumire;
        this.serii = serie;
        this.repetari= repetari;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getSerii() {
        return serii;
    }

    public void setSerii(int serii) {
        this.serii = serii;
    }

    public List<Repetari> getRepetari() {
        return repetari;
    }

    public void setRepetari(ArrayList<Repetari> repetari) {
        this.repetari = repetari;
    }

    @Override
    public String toString() {
        return "Exercitiu [denumire=" + denumire + ", serie=" + serii + ", repetari=" + repetari + "]";
    }


}
