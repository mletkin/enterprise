package org.ully.enterprise.units;

public class Force extends PhysicalUnit {

    public static final Force ZERO = Force.of(0);

    private Force(double value) {
        this.value = value;
    }

    @Override
    public String symbol() {
        return "N";
    }

    public static Force of(double value) {
        return new Force(value);
    }

}
