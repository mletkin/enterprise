package org.ully.enterprise.units;

/**
 * Base Class for physical units.
 */
public abstract class PhysicalUnit {

    private static final double EPSILON = 0.000001;
    protected double value;

    public double value() {
        return value;
    }

    public abstract String symbol();

    @Override
    public String toString() {
        return String.format("%.2f %s", value, symbol());
    }

    public boolean eqls(PhysicalUnit comp) {
        return (Math.abs(comp.value - value) < EPSILON);
    }
}
