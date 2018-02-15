package org.ully.enterprise.energy;

import org.ully.enterprise.Starship;

/**
 * interface for emulator cycle tasks.
 */
public interface Cycle {

    /**
     * sets the time interval.
     *
     * @param msec
     *            interval length in milliseconds
     * @return the Cycle instance
     */
    Cycle withDelta(long msec);

    /**
     * Calculate the changes for a single cycle.
     *
     * @param ship
     *            the starship to calculate
     */
    void calculateSingleCycle(Starship ship);

}
