package org.ully.enterprise.energy;

import org.ully.enterprise.Starship;

/**
 * Perform the power flow emulation.
 * <p>
 * Repeatedly performs the power flow emulation for a set of
 * {@code Circuit}-Objects with a configurable time interval in an endless loop.
 */
public class MovementEmulator extends Thread {

    private static final long DELTA_IN_MSEC = 10;

    private long delta;
    private MovementCycle cycle;
    private Starship ship;

    /**
     * Create an emulator object for a given time interval.
     *
     * @param delta
     *            time interval in milliseconds
     */
    private MovementEmulator(long delta) {
        this.delta = delta;
        cycle = new MovementCycle(delta);
    }

    /**
     * Creates an emulator for a given interval.
     *
     * @param delta
     *            time interval in milliseconds
     * @return the new emulator instance
     */
    public static MovementEmulator get(long delta) {
        return new MovementEmulator(delta);
    }

    /**
     * Creates an emulator for the standard interval.
     *
     * @return the new emulator instance
     */
    public static MovementEmulator get() {
        return new MovementEmulator(DELTA_IN_MSEC);
    }

    /**
     * Adds the ship to the emulator.
     *
     * @return the new emulator instance
     */
    public MovementEmulator with(Starship ship) {
        this.ship = ship;
        return this;
    }

    @Override
    public void run() {
        for (;;) {
            cycle.calculate(ship);
            try {
                sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
