package com.example.fit4bit_v002;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Aliment implements Serializable {
    private long Id;
    private String denumire;
    private int calorii;
    private double cantitate;
    @JsonProperty("macro")
    private Macronutrient macronutrienti;
    Aliment(){}

    Aliment(String denumire, int calorii, double cantitate, Macronutrient macronutrienti) {
        this.denumire = denumire;
        this.calorii = calorii;
        this.cantitate = cantitate;
        this.macronutrienti = macronutrienti;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCalorii() {
        return calorii;
    }

    public Macronutrient getMacronutrient()
    {
        return macronutrienti;
    }

    public void setCalorii(int calorii) {
        this.calorii = calorii;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Macronutrient getMacronutrienti() {
        return macronutrienti;
    }

    public void setMacronutrienti(Macronutrient macronutrienti) {
        this.macronutrienti = macronutrienti;
    }

    @Override
    public String toString() {
        return
                "Denumire aliment: " + denumire + "\n" +
                "calorii: " + calorii +"\n"+
                "cantitate: " + cantitate + "\n"+
                "macronutrienti: " + macronutrienti +"\n";
    }
}
