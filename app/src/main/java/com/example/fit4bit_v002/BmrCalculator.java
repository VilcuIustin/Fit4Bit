package com.example.fit4bit_v002;

import java.text.DecimalFormat;

public class BmrCalculator {
    private double BMR;

    public double calculeazaBMR(double masa, double inaltime, int varsta, String sex) {
        if (sex.equals("M")) {
            BMR = 10 * masa + 6.25 * inaltime - 5 * varsta + 5;
        }
        if (sex.equals("F")) {
            BMR = 10 * masa + 6.25 * inaltime - 5 * varsta + 5 - 161;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        BMR = Double.parseDouble(df.format(BMR));
        return BMR;
    }

    public void setBMR(double BMR) {
        this.BMR = BMR;
    }

    public String rezultatBMR(double BMR) {
        DecimalFormat df = new DecimalFormat("#.##");
        BMR = Double.parseDouble(df.format(BMR));
        return "Rata metabolica bazala este cantitatea de energie pe unitate"
                + " de timp de care are nevoie o persoana pentru a mentine corpul functional aflat in repaus.\n\n"
                + "Cantitatea dvs. de energie necesara masurata in calorii este urmatoarea:" + BMR;
    }

    public String calculeazaTotal(double BMR) {
        DecimalFormat df = new DecimalFormat("#.##");
        String total = "Daca sunteti sedentar ar trebui sa consumati ~ " + (Double.parseDouble(df.format(BMR * 1.2)))+ "\n\n"
                + "Daca sunteti putin activ (antrenamente usoare/sport 1-3 zile/saptamana) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(BMR * 1.375)))+ "\n\n"
                + "Daca sunteti activ moderat (antrenamente moderate/sport 3-5 zile/saptamana) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(BMR * 1.55))) + "\n\n"
                + "Daca sunteti foarte activ (antrenamente grele/sport 6-7 zile/saptamana) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(BMR * 1.725))) + "\n\n"
                + "Daca sunteti extrem de activ (antrenamente foarte grele/job de tipul fizic sau 2 x antrenamente pe zi in fiecare zi) ar trebui sa consumati ~"
                + (Double.parseDouble(df.format(BMR * 1.9)));
        return total;
    }
}
