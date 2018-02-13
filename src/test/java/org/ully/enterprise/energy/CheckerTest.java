package org.ully.enterprise.energy;

import static org.ully.enterprise.energy.TestUtil.c;

import org.junit.Assert;
import org.junit.Test;
import org.ully.enterprise.units.Power;

public class CheckerTest {

    Checker checker = new Checker();

    @Test
    public void validCircuitIsOk() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con = new ConstantConsumer(Power.of(4));

        Circuit c = c(c(con), c(c(sup)));
        Assert.assertTrue(checker.isSafe(c));
    }

    @Test
    public void multipleUsedComponentIsBad() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con = new ConstantConsumer(Power.of(4));

        Circuit c = c(c(con), c(c(sup), con));
        Assert.assertFalse(checker.isSafe(c));
    }

    @Test
    public void CircuitInCircuitIsBad() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con = new ConstantConsumer(Power.of(4));

        Circuit c2 = c(con);
        Circuit c = c(c2, c(c(sup, c2)));
        Assert.assertFalse(checker.isSafe(c));
    }

}
