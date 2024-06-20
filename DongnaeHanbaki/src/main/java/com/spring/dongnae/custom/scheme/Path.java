package com.spring.dongnae.custom.scheme;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Path {
	@JsonProperty("La")
    private double La;
	@JsonProperty("Ma")
    private double Ma;

    public double getLa() {
        return La;
    }

    public void setLa(double La) {
        this.La = La;
    }

    public double getMa() {
        return Ma;
    }

    public void setMa(double Ma) {
        this.Ma = Ma;
    }

    @Override
    public String toString() {
        return "Path [La=" + La + ", Ma=" + Ma + "]";
    }
}
