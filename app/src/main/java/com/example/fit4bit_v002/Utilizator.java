package com.example.fit4bit_v002;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.type.DateTime;

import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Utilizator {
    private String nume;
    private Date data_Nastere;
    private char sex;
    private double inaltime, masa;

    public Utilizator(double inaltime, double masa, String nume, Date varsta, char sex) {
        this.inaltime = inaltime;
        this.masa = masa;
        this.nume = nume;
        this.data_Nastere = varsta;
        this.sex = sex;
    }

    public Utilizator() {
    }

    public double getInaltime() {
        return inaltime;
    }

    public void setInaltime(double inaltime) {
        this.inaltime = inaltime;
    }

    public double getMasa() {
        return masa;
    }

    public void setMasa(double masa) {
        this.masa = masa;
    }

    public String getNume() {
        return nume;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Utilizator [nume=" + nume + ", varsta=" + data_Nastere + ", sex=" + sex + ", inaltime=" + inaltime + ", masa="
                + masa  + " }";
    }

    public Date getData_Nastere() {
        return data_Nastere;
    }

    public void setData_Nastere(Date data_Nastere) {
        this.data_Nastere = data_Nastere;
    }
}
