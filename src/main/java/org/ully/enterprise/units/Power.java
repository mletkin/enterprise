package org.ully.enterprise.units;

/**
 * Representation of a power unit.
 *
 * Power = Energy / second
 */
public class Power extends PhysicalUnit {

    public static final String SYMBOL = "E";
    public static final Power ZERO = new Power(0);

    public Power(double value) {
        this.value = value;
    }

    public static Power of(double value) {
        return new Power(value);
    }

    @Override
    public String symbol() {
        return SYMBOL;
    }

    public Energy toEnergy(long msec) {
        return Energy.of(value / 1000 * msec);
    }

}
