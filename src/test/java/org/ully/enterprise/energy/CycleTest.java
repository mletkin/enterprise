package org.ully.enterprise.energy;

import static org.junit.Assert.assertEquals;
import static org.ully.enterprise.energy.TestUtil.c;
import static org.ully.enterprise.energy.TestUtil.ship;

import org.junit.Assert;
import org.junit.Test;
import org.ully.enterprise.Component.Direction;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Shield;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

public class CycleTest {

    PowerCycle cycle = new PowerCycle().withDelta(1000);

    @Test
    public void singleConsumerWithoutEntropy() {
        Phaser one = new Phaser("");
        ConstantSupplier reactor = new ConstantSupplier(Power.of(1));

        cycle.calculateSingleCycle(ship(c(reactor, one)));

        Assert.assertEquals(1.0, one.getLoad().value(), 0.1d);
    }

    @Test
    public void unloadOneSheldToTheOther() {
        Shield src = new Shield("");
        src.load(Energy.of(10), 1000);
        src.setDirection(Direction.OUT);

        Shield dst = new Shield("");
        dst.setDirection(Direction.IN);

        cycle.calculateSingleCycle(ship(c(src, dst)));

        Assert.assertEquals(4.0, src.getLoad().value(), 0.1d);
        Assert.assertEquals(5.0, dst.getLoad().value(), 0.1d);
    }

    @Test
    public void unloadFromOnePhaserToTheOther() {
        Phaser src = new Phaser("");
        src.load(Energy.of(10), 1000);
        src.setDirection(Direction.OUT);

        Phaser dest = new Phaser("");
        dest.setDirection(Direction.IN);

        cycle.calculateSingleCycle(ship(c(src, dest)));

        assertEquals(5.0, src.getLoad().value(), 0.1d);
        assertEquals(5.0, dest.getLoad().value(), 0.1d);
    }

    @Test
    public void twoConsumersWithSufficientPowerSupply() {
        Shield s1 = new Shield("");
        Phaser p1 = new Phaser("");
        ConstantSupplier reactor = new ConstantSupplier(Power.of(10));

        cycle.calculateSingleCycle(ship(c(reactor, s1, p1)));

        assertEquals(5.0, s1.getLoad().value(), 0.1d);
        assertEquals(5.0, p1.getLoad().value(), 0.1d);
    }

    @Test
    public void twoConsumersWith50PercentPowerSupply() {
        Shield s1 = new Shield("");
        Phaser p1 = new Phaser("");
        ConstantSupplier reactor = new ConstantSupplier(Power.of(5));

        cycle.calculateSingleCycle(ship(c(reactor, s1, p1)));

        assertEquals(2.5, s1.getLoad().value(), 0.001d);
        assertEquals(2.5, p1.getLoad().value(), 0.001d);
    }

}
