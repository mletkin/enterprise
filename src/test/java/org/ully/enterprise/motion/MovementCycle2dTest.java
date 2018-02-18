package org.ully.enterprise.motion;

import static org.junit.Assert.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.ully.enterprise.units.Force;
import org.ully.enterprise.units.Vector;

/**
 * Moving heading north, heading vector is (1, 0)
 */
public class MovementCycle2dTest {

    MovementCycle2d cycle = new MovementCycle2d().withDelta(1);

    TestShip testShip = new TestShip();


    private static int STEPS = 72;

    protected static Stream<Vector> bearingProvider() {
        Vector[] list = new Vector[STEPS];
        for (int n = 0; n < STEPS; n++) {
            double phi = 2 * Math.PI * n / STEPS;
            list[n] = Vector.of(Math.sin(phi), Math.cos(phi));
        }
        return Stream.of(list);
    }

    /**
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 10 m/s<br>
     * => s = 5 m
     */
    @ParameterizedTest
    @MethodSource("bearingProvider")
    public void accelerationFromRestOneSecond(Vector bearing) {
        testShip.heading(bearing);
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        assertEquals(10.0, testShip.acceleration, 0.00001);
        assertEquals(10.0, testShip.speed(), 0.00001);
        assertEquals(+5.0, testShip.position().abs(), 0.01);
    }

    /**
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 10 m/s<br>
     * => s = 5 m
     */
    @ParameterizedTest
    @MethodSource("bearingProvider")
    public void accelerationFromRestTenSeconds(Vector bearing) {
        testShip.heading(bearing);
        for (int n = 0; n < 10000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        assertEquals(10.0, testShip.acceleration, 0.00001);
        assertEquals(100.0, testShip.speed(), 0.00001);
        assertEquals(+500.0, testShip.position().abs(), 0.1);
    }

    /**
     * v0 = 10 m/s<br>
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 20 m/s<br>
     * => s = 15 m
     */
    @ParameterizedTest
    @MethodSource("bearingProvider")
    public void accelerationFromMotion(Vector bearing) {
        testShip.heading(bearing);
        testShip.bearing(bearing.multi(10));
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        assertEquals(10.0, testShip.acceleration, 0.00001);
        assertEquals(20.0, testShip.speed(), 0.00001);
        assertEquals(15.0, testShip.position().abs(), 0.01);
    }

    /**
     * a = 10 m/s^2<br>
     * t = 1000 * 1 ms<br>
     * => v = 10 m/s<br>
     * => s = 5 m
     */
    @ParameterizedTest
    @MethodSource("bearingProvider")
    public void OneSeondAccelerationThenEngineStopForOneSecond(Vector bearing) {
        testShip.heading(bearing);
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        testShip.force = Force.ZERO;
        for (int n = 0; n < 1000; n++) {
            cycle.calculateSingleCycle(testShip);
        }

        assertEquals(0.0, testShip.acceleration, 0.00001);
        assertEquals(10.0, testShip.speed(), 0.00001);
        assertEquals(15.0, testShip.position().abs(), 0.1);
    }

}
