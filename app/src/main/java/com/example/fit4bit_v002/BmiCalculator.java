package com.example.fit4bit_v002;
import java.text.DecimalFormat;
public class BmiCalculator {
    private double BMI;
    private String rezultatBMI;

    public double calculeazaBMI(double masa, double inaltime) {
        BMI = masa / (Math.pow(inaltime, 2));
        DecimalFormat df = new DecimalFormat("#.##");
        BMI = Double.parseDouble(df.format(BMI));
        return BMI;
    }

    public String rezultatBMI(double BMI) {
        if (BMI < 18.5) {
            rezultatBMI = "Sunteti subponderal.";
        }
        if ((BMI > 25) && (BMI < 30)) {
            rezultatBMI = "Sunteti supraponderal.";
        }
        if (BMI >= 30) {
            rezultatBMI = "Sunteti obez.";
        }
        if (BMI >= 18.5 && BMI <= 25) {
            rezultatBMI = "Va aflati la masa optima.";
        }
        return rezultatBMI;
    }

    @Override
    public String toString() {
        return "    Indicele de masă corporală (IMC) (în engleză Body mass index, sau BMI)\n"+
                "este un indicator statistic al masei unei persoane raportată la înălțimea persoanei respective.\n\n"+
                "   Deși e folositor pentru majoritatea oamenilor, IMC nu funcționează pentru toată lumea.\n\n" +
                "   NU se potrivește copiilor sau oamenilor în vârstă. De asemenea nu e relevant dacă aveți mulți mușchi din cauza că faceți mult sport. " +
                "Mușchii cântăresc mai mult în comparație cu depozitele de grăsime, deci IMC va fi mai mare în cazul unui corp atletic.\n\n" +
                "   De exemplu jucătorii de rugby au un IMC care indică stare de obezitate, chiar dacă nu au grăsime corporală în exces.";
    }
}
