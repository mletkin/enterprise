package org.ully.enterprise.units;

public class Power {

    public static final Power ZERO = new Power(0);
    private double value;

    public Power(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

}
