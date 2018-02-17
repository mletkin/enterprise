package org.ully.enterprise.units;

public enum Compass {

    N(0, 0, 1),
    NNE(22.5, Const.Y_22, Const.X_22),
    NE(45, 1, 1),
    ENE(67.5, Const.X_22, Const.Y_22),
    E(90, 1, 0),
    ESE(112.5, Const.X_22, -Const.Y_22),
    SE(135, 1, -1),
    SSE(157.5, Const.Y_22, -Const.X_22),
    S(180, 0, -1),
    SSW(202.5, -Const.Y_22, -Const.X_22),
    SW(225, -1, -1),
    WSW(247.5, -Const.X_22, -Const.Y_22),
    W(270, -1, 0),
    WNW(292.5, -Const.X_22, Const.Y_22),
    NW(315, -1, 1),
    NNW(337.5, -Const.Y_22, Const.X_22),;

    double x;
    double y;
    double grad;

    Compass(double grad, double x, double y) {
        this.x = x;
        this.y = y;
        this.grad = grad;
    }

    public Vector vector() {
        return Vector.of(x, y);
    }

    public double rad() {
        return grad / 180 * Math.PI;
    }

    public double deg() {
        return grad;
    }

    private static class Const {
        private static final double X_22 = Math.abs(Math.cos(22.5 / 180 * Math.PI));
        private static final double Y_22 = Math.abs(Math.sin(22.5 / 180 * Math.PI));

        private static final double X45 = Math.abs(Math.sin(45 / 180 * Math.PI));
        private static final double Y45 = Math.abs(Math.cos(45 / 180 * Math.PI));
    }

}
