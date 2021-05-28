package com.example.fit4bit_v002;

import com.example.fit4bit_v002.Exercitiu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Antrenament implements  Serializable {
    private long id;
    private String denumire;
    private ArrayList<Exercitiu> exercitii;
    private double durata;

    public Antrenament(String denumire, ArrayList<Exercitiu> exercitii, double durata) {
        this.denumire = denumire;
        this.exercitii = exercitii;
        this.durata = durata;
    }
    public Antrenament(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Antrenament(String denumire, double durata) {
        this.denumire = denumire;
        this.exercitii = new ArrayList<Exercitiu>();
        this.durata = durata;
    }

    public void setExercitii(ArrayList<Exercitiu> exercitii) {
        this.exercitii = exercitii;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public ArrayList<Exercitiu> getExercitii() {
        return exercitii;
    }


    public double getDurata() {
        return durata;
    }

    public void setDurata(double durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "Antrenament{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", exercitii=" + exercitii +
                ", durata=" + durata +
                '}';
    }
}
