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

    /**
     * The energy of the power, applied over the given time.
     *
     * @param msec
     *            time in milliseconds
     * @return the collected energy
     */
    public Energy toEnergy(long msec) {
        return Energy.of(value / 1000 * msec);
    }

    public Power neg() {
        return of(-value);
    }

    public Power multi(double factor) {
        return Power.of(value * factor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Power) {
            return super.equals(obj);
        }
        return false;
    }
}
