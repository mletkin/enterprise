package org.ully.enterprise.energy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.fleet.Pegasus;
import org.ully.enterprise.fleet.Potemkin;

public class CheckerTest2 {

    Checker checker = new Checker();

    @Test
    public void enterpriseIsOk() {
        assertTrue(checker.isSafe(new Enterprise().powerSystem()));
    }

    @Test
    public void pegasusIsDefect() {
        assertFalse(checker.isSafe(new Pegasus().powerSystem()));
    }

    @Test
    public void potemkinIsOk() {
        assertTrue(checker.isSafe(new Potemkin().powerSystem()));
    }

}
