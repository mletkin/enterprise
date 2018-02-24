package org.ully.enterprise.units;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class VectorTest {

    @ParameterizedTest
    @EnumSource(Compass.class)
    public void checkDegMethod(Compass direction) {
        Assert.assertEquals(direction.deg(), direction.vector().deg(), 0.01);
    }

    @ParameterizedTest
    @EnumSource(Compass.class)
    public void checkPolarMethod(Compass direction) {
        Assert.assertEquals(direction.deg(), Vector.polar(direction.rad(), 1).deg(), 0.01);
    }

    @Test
    public void rotateVectorBy90DegreesClockwise() {
        Vector v = Vector.of(1, 1).rotate(Math.PI / 2);
        Assert.assertEquals(+1, v.x(), 0.01);
        Assert.assertEquals(-1, v.y(), 0.01);
    }

    @Test
    public void rotateVectorBy360DegreesClockwise() {
        Vector v = Vector.of(1, 1).rotate(2 * Math.PI);
        Assert.assertEquals(+1, v.x(), 0.01);
        Assert.assertEquals(+1, v.y(), 0.01);
    }

    @Test
    public void rotateVectorBy90DegreesCounterClockWise() {
        Vector v = Vector.of(1, 1).rotate(-Math.PI / 2);
        Assert.assertEquals(-1, v.x(), 0.01);
        Assert.assertEquals(+1, v.y(), 0.01);
    }

    @Test
    public void rotateVectorBy360DegreesCounterClockWise() {
        Vector v = Vector.of(1, 1).rotate(- 2* Math.PI);
        Assert.assertEquals(+1, v.x(), 0.01);
        Assert.assertEquals(+1, v.y(), 0.01);
    }

}
