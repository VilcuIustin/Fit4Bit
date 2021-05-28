package com.example.fit4bit_v002;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class BmrCalculatorUtilizator {

    private Utilizator utilizator;
    private double BMR;
    private String rezultatBMR;
    private ArrayList<Integer> bmrResultsList;

    BmrCalculatorUtilizator(Utilizator uti) {
        this.utilizator = uti;
    }

    public double getBMR() {
        return this.BMR;
    }

    public void setBMR(double Bmr) {
        this.BMR = Bmr;
    }

    public double calculeazaBMR() {
        if (utilizator.getSex() == 'M') {

            BMR = 10 * utilizator.getMasa() + 6.25 * utilizator.getInaltime() *100- 5 * (new Date().getYear()-utilizator.getData_Nastere().getYear()) + 5;
        }
        if (utilizator.getSex() == 'F') {
            BMR = 10 * utilizator.getMasa() + 6.25 * utilizator.getInaltime()*100 - 5 * (new Date().getYear()-utilizator.getData_Nastere().getYear()) - 161;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(BMR);
        BMR = Double.parseDouble(df.format(BMR));
        System.out.println(BMR);
        return BMR;
    }

    public String rezultatBMR() {
        return "Rata metabolica bazala este cantitatea de energie pe unitate"
                + "de timp de care are <br>nevoie o persoana pentru a mentine corpul functional aflat in repaus."
                + "Cantitatea dvs. de energie masurata in calorii este urmatoarea:" + getBMR() + ".";
    }

    public String calculeazaTotal() {
        DecimalFormat df = new DecimalFormat("#");
        String total = "\nDaca sunteti sedentar ar trebui sa consumati ~ "
                + (Double.parseDouble(df.format(this.getBMR() * 1.2))) + " ." + "\n\n"
                + "Daca sunteti putin activ (antrenamente usoare/sport 1-3 zile/saptamana) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(this.getBMR() * 1.375))) + " ." + "\n\n"
                + "Daca sunteti activ moderat (antrenamente moderate/sport 3-5 zile/saptamana) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(this.getBMR() * 1.55))) + " .\n\n"
                + "Daca sunteti foarte activ (antrenamente grele/sport 6-7 zile/saptamana) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(this.getBMR() * 1.725))) + " .\n\n"
                + "Daca sunteti extrem de activ (antrenamente foarte grele/job de tipul fizic sau 2 x antrenamente pe zi in fiecare zi) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(this.getBMR() * 1.9))) + ".";
        return total;
    }
    public ArrayList<Integer> seteazaListaCuToateBmr(double bmr){
        DecimalFormat df = new DecimalFormat("#");
        bmrResultsList = new ArrayList<>();
        double bmr1=bmr*1.2;
        bmrResultsList.add(Integer.parseInt(df.format(bmr1)));
        double bmr2=bmr*1.375;
        bmrResultsList.add(Integer.parseInt(df.format(bmr2)));
        double bmr3=bmr*1.55;
        bmrResultsList.add(Integer.parseInt(df.format(bmr3)));
        double bmr4=bmr*1.725;
        bmrResultsList.add(Integer.parseInt(df.format(bmr4)));
        double bmr5=bmr*1.9;
        bmrResultsList.add(Integer.parseInt(df.format(bmr5)));
        return bmrResultsList;
    }
}
