package com.example.fit4bit_v002;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repetari implements Serializable {
    @JsonProperty("repetareSerie")
    private int repetare;

    Repetari(){}
    Repetari(int i){
        this.repetare = i;
    }

    public int getRepetare() {
        return repetare;
    }

    public void setRepetare(int repetare) {
        this.repetare = repetare;
    }

    @Override
    public String toString() {
        return "Repetari{" +
                "repetare=" + repetare +
                '}';
    }
}