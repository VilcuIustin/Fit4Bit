package com.example.fit4bit_v002;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Macronutrient implements Serializable {
    private double proteine;
    private double carbohidrati;
    private double grasimi;

    Macronutrient(double proteine, double carbohidrati, double grasimi) {
        this.carbohidrati = carbohidrati;
        this.proteine = proteine;
        this.grasimi = grasimi;
    }

    Macronutrient() {
    }

    double getProteine() {
        return this.proteine;
    }

    double getCarbohidrati() {
        return this.carbohidrati;
    }

    double getGrasimi() {
        return this.grasimi;
    }

    void setGrasimi(double grasimi) {
        this.grasimi = grasimi;
    }

    void setProteine(double proteine) {
        this.proteine = proteine;
    }

    void setCarbohidrati(double carbohidrati) {
        this.carbohidrati = carbohidrati;
    }

    @Override
    public String toString() {
        return "\nproteine: " + proteine +"g" +"\n" +"carbohridrati: " + carbohidrati + "g" +"\n"+"grasimi: " + grasimi
                + "g";
    }

}
