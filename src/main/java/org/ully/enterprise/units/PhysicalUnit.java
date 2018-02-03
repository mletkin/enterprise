package org.ully.enterprise.units;

/**
 * Base Class for physical units.
 */
public abstract class PhysicalUnit {

    protected double value;

    public double value() {
        return value;
    }

    public abstract String symbol();

    @Override
    public String toString() {
        return String.format("%.2f %s", value, symbol());
    }

}
