package org.ully.enterprise.energy;

import org.ully.enterprise.Starship;

/**
 * Perform the power flow emulation.
 * <p>
 * Repeatedly performs the power flow emulation for a set of
 * {@code Circuit}-Objects with a configurable time interval in an endless loop.
 */
public class Emulator extends Thread {

    private static final long DELTA_IN_MSEC = 10;

    private long delta = DELTA_IN_MSEC;
    private Cycle cycle;
    private Starship ship;

    /**
     * Creates an emulator object with the default time interval.
     */
    private Emulator() {
        // prenent instanciation
    }

    /**
     * Creates an emulator for the standard interval.
     *
     * @return the new emulator instance
     */
    public static Emulator get() {
        return new Emulator();
    }

    /**
     * Creates an emulator for a given interval.
     *
     * @param delta
     *            time interval in milliseconds
     * @return the {@code Emulator} Object
     */
    public Emulator withDelta(long delta) {
        this.delta = delta;
        return this;
    }

    /**
     * Use the cycle object for calculation.
     *
     * @param cycle
     *            cycle to use for calculation
     * @return the {@code Emulator} Object
     */
    public Emulator with(Cycle cycle) {
        this.cycle = cycle.withDelta(delta);
        return this;
    }

    /**
     * Use the given ship.
     *
     * @param ship
     *            the ship to use for the emulation
     * @return the {@code Emulator} Object
     */
    public Emulator with(Starship ship) {
        this.ship = ship;
        return this;
    }

    @Override
    public void run() {
        for (;;) {
            cycle.calculateSingleCycle(ship);
            try {
                sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
