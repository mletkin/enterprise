package org.ully.enterprise.energy;

import org.junit.Assert;
import org.junit.Test;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.fleet.Pegasus;
import org.ully.enterprise.fleet.Potemkin;

public class CheckerTest2 {

    Checker checker = new Checker();

    @Test
    public void enterpriseIsOk() {
        Assert.assertTrue(checker.isSafe(new Enterprise().getCircuits()));
    }

    @Test
    public void pegasusIsDefect() {
        Assert.assertFalse(checker.isSafe(new Pegasus().getCircuits()));
    }

    @Test
    public void potemkinIsOk() {
        Assert.assertTrue(checker.isSafe(new Potemkin().getCircuits()));
    }

}
