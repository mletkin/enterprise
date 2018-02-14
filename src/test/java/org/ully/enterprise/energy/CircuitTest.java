package org.ully.enterprise.energy;

import static org.junit.Assert.assertEquals;
import static org.ully.enterprise.energy.TestUtil.c;

import org.junit.Test;
import org.ully.enterprise.Component;
import org.ully.enterprise.units.Power;

public class CircuitTest {

    static private Component s() {
        return new ConstantSupplier(Power.ZERO);
    }
    @Test
    public void singleCircuit_2() {
        Circuit c1 = c();
        assertEquals(1, c1.getAllComponents().count());
    }

    @Test
    public void singleCircuitSingleComponent_3() {
        Circuit c1 = c(s());
        assertEquals(2, c1.getAllComponents().count());
    }

    @Test
    public void oneCircuitTwoComponents_6() {
        Circuit c1 = c(s(), s());
        assertEquals(3, c1.getAllComponents().count());
    }

    @Test
    public void twoCircuitsOneComponent() {
        Circuit c1 = c(c(s()));
        assertEquals(4, c1.getAllComponents().count());
    }

    @Test
    public void twoCircuitTwoComponents_6() {
        Circuit c1 = c(s(), c(s()));
        assertEquals(5, c1.getAllComponents().count());
    }

}
