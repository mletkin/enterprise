package org.ully.enterprise.units;

import org.ully.enterprise.util.Util;

/**
 * A two dimensional vector.
 * <p>
 * Polar coordinates start north (cartesian (0, 1) with angle 0<br>
 * and run clockwise.
 */
public class Vector {

    public static final Vector ZERO = of(0, 0);

    private double x;
    private double y;

    /**
     * Creates a vector objet from cartesian coordinates.
     *
     * @param x
     * @param y
     * @return
     */
    public static Vector of(double x, double y) {
        return new Vector(x, y);
    }

    /**
     * Creates a vector object from polar coordinates.
     *
     * @param phi
     * @param length
     * @return
     */
    public static Vector polar(double phi, double length) {
        return new Vector(length * Math.sin(phi), length * Math.cos(phi));
    }

    /**
     * Create objects only with factory methods.
     *
     * @param x
     * @param y
     */
    private Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the first coordinate component.
     *
     * @return
     */
    public double x() {
        return x;
    }

    /**
     * Returns the second coordinate component.
     *
     * @return
     */
    public double y() {
        return y;
    }

    /**
     * Adds a vector to this object.
     *
     * @param b
     * @return
     */
    public Vector add(Vector b) {
        return of(x + b.x, y + b.y);
    }

    /**
     * Adds the negated Vector to this object.
     *
     * @param b
     * @return
     */
    public Vector sub(Vector b) {
        return of(x - b.x, y - b.y);
    }

    /**
     * Calculates the scalar product.
     *
     * @param a
     * @return
     */
    public Vector multi(double a) {
        return of(a * x, a * y);
    }

    /**
     * Returns the absolute Value (the length) of the vector.
     *
     * @return
     */
    public double abs() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns the vector's angle in radian.
     *
     * @return
     */
    public double rad() {

        if (Util.isZero(x)) {
            return y >= 0 ? 0 : Math.PI;
        }

        if (Util.isZero(y)) {
            return x > 0 ? Math.PI / 2 : 1.5 * Math.PI;
        }

        return (x > 0 ? Math.PI / 2 : 1.5 * Math.PI) - Math.atan(y / x);
    }

    /**
     * Returns the vector's angle in degrees.
     *
     * @return
     */
    public double deg() {
        return rad() / Math.PI * 180;
    }

    /**
     * Rotates Turns the vector by the given radian angle.
     * <p>
     * positive value is clockwise.
     *
     * @param angle
     * @return
     */
    public Vector rotate(double angle) {
        return polar(rad() + angle, abs());
    }

    @Override
    public String toString() {
        return String.format("c(%.2f, %.2f) p(%.2f, %.2f)", x, y, rad(), abs());
    }
}
