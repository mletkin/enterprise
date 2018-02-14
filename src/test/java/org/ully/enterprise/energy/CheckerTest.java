package org.ully.enterprise.energy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.ully.enterprise.energy.TestUtil.c;

import org.junit.Test;
import org.ully.enterprise.units.Power;

public class CheckerTest {

    Checker checker = new Checker();
    ConstantSupplier sup = new ConstantSupplier(Power.of(5));
    ConstantConsumer con = new ConstantConsumer(Power.of(4));

    @Test
    public void validCircuitIsOk() {
        Circuit c = c(c(con), c(c(sup)));

        assertTrue(checker.isSafe(c));
    }

    @Test
    public void multipleUsedComponentIsBad() {
        Circuit c = c(c(con), c(c(sup), con));

        assertFalse(checker.isSafe(c));
    }

    @Test
    public void CircuitInCircuitIsBad() {
        Circuit c2 = c(con);
        Circuit c = c(c2, c(c(sup, c2)));

        assertFalse(checker.isSafe(c));
    }

}
