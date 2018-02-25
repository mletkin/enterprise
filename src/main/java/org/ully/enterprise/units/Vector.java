package org.ully.enterprise.units;

import org.ully.enterprise.util.Util;

/**
 * Immutable representation of a two dimensional vector.
 * <p>
 * Polar coordinates start north (cartesian (0, 1) with angle 0<br>
 * and run clockwise.
 */
public class Vector {

    public static final Vector ZERO = of(0, 0);

    private double x;
    private double y;

    /**
     * Creates a vector object from cartesian coordinates.
     *
     * @param x
     *            value on the x axis
     * @param y
     *            value on the y axis
     * @return the vector object
     */
    public static Vector of(double x, double y) {
        return new Vector(x, y);
    }

    /**
     * Creates a vector object from polar coordinates.
     *
     * @param phi
     *            the angle clockwise from the positive y axis
     * @param length
     *            length of the vector
     * @return the vector object
     */
    public static Vector polar(double phi, double length) {
        return new Vector(length * Math.sin(phi), length * Math.cos(phi));
    }

    /**
     * Object creation is restricted to the factoy methods.
     *
     * @param x
     *            value on the x axis
     * @param y
     *            value on the y axis
     */
    private Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the first coordinate component.
     *
     * @return the value on the x axis
     */
    public double x() {
        return x;
    }

    /**
     * Returns the second coordinate component.
     *
     * @return the value on the y axis
     */
    public double y() {
        return y;
    }

    /**
     * Performs a vector addition.
     *
     * @param b
     *            the vector to add
     * @return a new vector object
     */
    public Vector add(Vector b) {
        return of(x + b.x, y + b.y);
    }

    /**
     * Performs a vector substraction.
     *
     * @param b
     *            the vector to subtract
     * @return a new vector object
     */
    public Vector sub(Vector b) {
        return of(x - b.x, y - b.y);
    }

    /**
     * Calculates the scalar product.
     *
     * @param a
     *            the factor for the multiplication
     * @return a new vector object
     */
    public Vector multi(double a) {
        return of(a * x, a * y);
    }

    /**
     * Returns the absolute Value (the length) of the vector.
     *
     * @return the absolute value
     */
    public double abs() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns the vector's angle in radian.
     *
     * @return the angle in radian
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
     * @return the angle in degrees
     */
    public double deg() {
        return rad() / Math.PI * 180;
    }

    /**
     * Rotates the vector by the given radian angle.
     *
     * A positive angle value means clockwise rotation.
     *
     * @param angle
     *            the angle value to rotate
     * @return a new vector object
     */
    public Vector rotate(double angle) {
        return polar(rad() + angle, abs());
    }

    @Override
    public String toString() {
        return String.format("c(%.2f, %.2f) p(%.2f, %.2f)", x, y, rad(), abs());
    }
}
