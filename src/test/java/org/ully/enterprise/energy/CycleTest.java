package org.ully.enterprise.energy;

import org.junit.Assert;
import org.junit.Test;
import org.ully.enterprise.Component.Direction;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

public class CycleTest {

    @Test
    public void singleConsumerWithoutEntropy() {
        Phaser one = new Phaser("");
        Reactor reactor = mkReactor(Power.of(1));
        Circuit c = new Circuit(reactor, one);

        new Cycle(c).calculate(1000);
        Assert.assertEquals(1.0, one.getLoad().value(), 0.1d);
    }

    private Reactor mkReactor(Power flow) {
        Reactor reactor = new Reactor("", Power.of(10));
        reactor.setFlow(flow);
        return reactor;
    }

    @Test
    public void unloadOneSheldToTheOther() {
        Shield src = new Shield("");
        src.load(Energy.of(10), 1000);
        src.setDirection(Direction.OUT);

        Shield dst = new Shield("");
        dst.setDirection(Direction.IN);

        Circuit c = new Circuit(src, dst);
        new Cycle(c).calculate(1000);

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

        Circuit c = new Circuit(src, dest);

        new Cycle(c).calculate(1000);
        Assert.assertEquals(5.0, src.getLoad().value(), 0.1d);
        Assert.assertEquals(5.0, dest.getLoad().value(), 0.1d);
    }

    @Test
    public void twoConsumersWithSufficientPowerSupply() {
        Shield s1 = new Shield("");
        Phaser p1 = new Phaser("");
        Circuit c = new Circuit(mkReactor(Power.of(10)), s1, p1);

        new Cycle(c).calculate(1000);

        Assert.assertEquals(5.0, s1.getLoad().value(), 0.1d);
        Assert.assertEquals(5.0, p1.getLoad().value(), 0.1d);
    }

    @Test
    public void twoConsumersWith50PercentPowerSupply() {
        Shield s1 = new Shield("");
        Phaser p1 = new Phaser("");
        Circuit c = new Circuit(mkReactor(Power.of(5)), s1, p1);

        new Cycle(c).calculate(1000);

        Assert.assertEquals(2.5, s1.getLoad().value(), 0.001d);
        Assert.assertEquals(2.5, p1.getLoad().value(), 0.001d);
    }

}
