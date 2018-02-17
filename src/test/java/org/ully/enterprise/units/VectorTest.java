package org.ully.enterprise.units;

import org.junit.Assert;
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

}
