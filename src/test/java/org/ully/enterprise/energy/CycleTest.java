package org.ully.enterprise.energy;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.ully.enterprise.Component.Direction;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

public class CycleTest {

    Circuit c = new Circuit();

    @Ignore
    @Test
    public void singleConsumer() {
        Shield shield = new Shield("");
        c.supplier = Arrays.asList(new Reactor("", Power.of(1)));
        c.consumer = Arrays.asList(shield);

        new Cycle(c).calculate(1000);
        Assert.assertEquals(1.0, shield.getLoad().value(), 0.1d);
    }

    @Ignore
    @Test
    public void twoShields() {
        Shield shield1 = new Shield("");
        shield1.load(Energy.of(1), 1000);
        shield1.setDirection(Direction.OUT);
        Shield shield2 = new Shield("");

        c.consumer = Arrays.asList(shield1, shield2);

        new Cycle(c).calculate(1000);
        Assert.assertEquals(1.0, shield1.getLoad().value(), 0.1d);
    }

    //
//    @Ignore
//    @Test
//    public void twoConsumersWithSufficientPowerSupply() {
//        Circuit c = new Circuit();
//        c.supplier = Arrays.asList(new Reactor("", Power.of(10)));
//        c.consumer = Arrays.asList(new Shield(""), new Shield(""));
//
//        new Cycle(c).calculate(1000);
//        Assert.assertEquals(1.0, c.consumer.get(0).getLoad().value(), 0.1d);
//        Assert.assertEquals(1.0, c.consumer.get(1).getLoad().value(), 0.1d);
//    }
//
//    @Ignore
//    @Test
//    public void twoConsumersWith50PercentPowerSupply() {
//        Circuit c = new Circuit();
//        c.supplier = Arrays.asList(new Reactor("", Power.of(1)));
//        c.consumer = Arrays.asList(new Shield(""), new Shield(""));
//
//        new Cycle(c).calculate(1000);
//        Assert.assertEquals(.5, c.consumer.get(0).getLoad().value(), 0.001d);
//        Assert.assertEquals(.5, c.consumer.get(1).getLoad().value(), 0.001d);
//    }

}
