package org.ully.enterprise.units;

/**
 * Base Class for physical units.
 */
public abstract class PhysicalUnit {

    private static final double EPSILON = 0.000_000_001;
    protected double value;

    /**
     * Get the value as scalar.
     *
     * @return
     */
    public double value() {
        return value;
    }

    /**
     * Define the units symbol.
     *
     * @return string representation of the unit symbol
     */
    public abstract String symbol();

    @Override
    public String toString() {
        return String.format("%.2f %s", value, symbol());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PhysicalUnit) {
            return (Math.abs(((PhysicalUnit) obj).value - value) < EPSILON);
        }
        return false;
    }

    /**
     * Calculate maximum of two unit ojects.
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends PhysicalUnit> T min(T a, T b) {
        return (a.value < b.value) ? a : b;
    }

    /**
     * calculate maximum of two unit ojects.
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends PhysicalUnit> T max(T a, T b) {
        return (a.value > b.value) ? a : b;
    }
}
