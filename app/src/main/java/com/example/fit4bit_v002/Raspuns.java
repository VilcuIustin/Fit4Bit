package com.example.fit4bit_v002;




/*      Clasa pentru a putea trimite statusul requestului, rezultatul dar si o posibila eroare atat de la backend cat si din android */

public class Raspuns {
    private boolean status;
    private Object rezultat;
    private String eroare;

    public Raspuns() {
    }

    public Raspuns(boolean status, Object rezultat, String eroare) {
        this.status = status;
        this.rezultat = rezultat;
        this.eroare = eroare;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getRezultat() {
        return rezultat;
    }

    public void setRezultat(Object rezultat) {
        this.rezultat = rezultat;
    }

    public String getEroare() {
        return eroare;
    }

    public void setEroare(String eroare) {
        this.eroare = eroare;
    }

    @Override
    public String toString() {
        return "Raspuns{" +
                "status=" + status +
                ", rezultat=" + rezultat.toString() +
                ", eroare='" + eroare + '\'' +
                '}';
    }
}

