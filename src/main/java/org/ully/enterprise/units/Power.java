package org.ully.enterprise.units;

/**
 * Representation of a power unit.
 *
 * Power = Energy / second
 */
public class Power {

    public static final Power ZERO = new Power(0);
    private double value;

    public Power(double value) {
        this.value = value;
    }

    public static Power of(double value) {
        return new Power(value);
    }

    public double value() {
        return value;
    }

    public Energy toEnergy(long msec) {
        return Energy.of(value / 1000 * msec);
    }
}
