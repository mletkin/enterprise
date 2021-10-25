package org.ully.enterprise.units;

/**
 * Base Class for physical units.
 */
public abstract class PhysicalUnit {

    private static final double EPSILON = 0.000_000_001;
    protected double value;

    /**
     * Gets the value as scalar.
     *
     * @return value of the object
     */
    public double value() {
        return value;
    }

    /**
     * Returns the units symbol.
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
            return Math.abs(((PhysicalUnit) obj).value - value) < EPSILON;
        }
        return false;
    }

    /**
     * Calculate the minimum of two unit ojects.
     *
     * @param <T>
     *                return type, subtype of PhysicalUnit
     * @param a
     *                first unit
     * @param b
     *                second unit
     * @return unit with minimum value
     */
    public static <T extends PhysicalUnit> T min(T a, T b) {
        return a.value < b.value ? a : b;
    }

    /**
     * Calculate the maximum of two unit ojects.
     *
     * @param <T>
     *                return type, subtype of PhysicalUnit
     * @param a
     *                first unit
     * @param b
     *                second unit
     * @return unit with maximum value
     */
    public static <T extends PhysicalUnit> T max(T a, T b) {
        return a.value > b.value ? a : b;
    }
}
