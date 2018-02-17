package org.ully.enterprise.motion;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.ully.enterprise.Engine;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.units.Force;

public class MovementCycleTest {

    MovementCycle cycle = new MovementCycle().withDelta(1);

    static abstract class TestShip extends Starship {
        Force force = Force.of(10);
        public TestShip() {
            super("");
        }
    }

    TestShip testShip = new TestShip() {
        @Override
        public Stream<Engine> engines() {
            return Stream.of(mass -> force);
        }

        @Override
        public Circuit powerSystem() {
            return null;
        }

        @Override
        public double mass() {
            return 1;
        }
    };

    /**
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 10 m/s<br>
     * => s = 5 m
     */
    @Test
    public void accelerationFromRestOneSecond() {
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        Assert.assertEquals(10.0, testShip.acceleration, 0.00001);
        Assert.assertEquals(10.0, testShip.speed, 0.00001);
        Assert.assertEquals(+5.0, testShip.dist, 0.01);
    }

    /**
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 10 m/s<br>
     * => s = 5 m
     */
    @Test
    public void accelerationFromRestTenSeconds() {
        for (int n = 0; n < 10000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        Assert.assertEquals(10.0, testShip.acceleration, 0.00001);
        Assert.assertEquals(100.0, testShip.speed, 0.00001);
        Assert.assertEquals(+500.0, testShip.dist, 0.1);
    }

    /**
     * v0 = 10 m/s<br>
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 20 m/s<br>
     * => s = 15 m
     */
    @Test
    public void accelerationFromMotion() {
        testShip.speed = 10;
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        Assert.assertEquals(10.0, testShip.acceleration, 0.00001);
        Assert.assertEquals(20.0, testShip.speed, 0.00001);
        Assert.assertEquals(15.0, testShip.dist, 0.01);
    }

    /**
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 10 m/s<br>
     * => s = 5 m
     */
    @Test
    public void OneSeondAccelerationThenEngineStopForOneSecond() {
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        Assert.assertEquals(10.0, testShip.acceleration, 0.00001);
        Assert.assertEquals(10.0, testShip.speed, 0.00001);
        Assert.assertEquals(+5.0, testShip.dist, 0.01);

        testShip.force = Force.ZERO;
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        Assert.assertEquals(0.0, testShip.acceleration, 0.00001);
        Assert.assertEquals(10.0, testShip.speed, 0.00001);
        Assert.assertEquals(15.0, testShip.dist, 0.1);
    }

}
