package org.ully.enterprise.energy;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.units.Power;

class CycleTest {

    @Test
    void singleConsumer() {
        Circuit c = new Circuit();
        c.supplier = new Reactor(Power.of(1));
        c.consumer = Arrays.asList(new Shield());

        new Cycle(c).calculate(1000);
        Assert.assertEquals(1.0, c.consumer.get(0).getLoad().value(), 0.1d);
    }

    @Test
    void twoConsumersWithSufficientPowerSupply() {
        Circuit c = new Circuit();
        c.supplier = new Reactor(Power.of(10));
        c.consumer = Arrays.asList(new Shield(), new Shield());

        new Cycle(c).calculate(1000);
        Assert.assertEquals(1.0, c.consumer.get(0).getLoad().value(), 0.1d);
        Assert.assertEquals(1.0, c.consumer.get(1).getLoad().value(), 0.1d);
    }

    @Test
    void twoConsumersWith50PercentPowerSupply() {
        Circuit c = new Circuit();
        c.supplier = new Reactor(Power.of(1));
        c.consumer = Arrays.asList(new Shield(), new Shield());

        new Cycle(c).calculate(1000);
        Assert.assertEquals(.5, c.consumer.get(0).getLoad().value(), 0.001d);
        Assert.assertEquals(.5, c.consumer.get(1).getLoad().value(), 0.001d);
    }

}
