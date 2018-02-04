package org.ully.enterprise.units;

/**
 * representation of an energy unit.
 */
public class Energy extends PhysicalUnit {

    public static final String SYMBOL = "E";
    public static final Energy ZERO = new Energy(0);

    public static Energy of(double value) {
        return new Energy(value);
    }

    private Energy(double value) {
        this.value = value;
    }

    @Override
    public String symbol() {
        return SYMBOL;
    }

    public Power toPower(long msec) {
        return Power.of(value * 1000 / msec);
    }

    public Energy add(Energy energy) {
        return new Energy(value + energy.value);
    }

    public Energy sub(Energy energy) {
        return new Energy(value - energy.value);
    }

    public boolean le(Energy energy) {
        return value <= energy.value;
    }

    public boolean ge(Energy energy) {
        return value >= energy.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Energy) {
            return super.equals(obj);
        }
        return false;
    }

}
