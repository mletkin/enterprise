package org.ully.enterprise.energy;

import org.ully.enterprise.Starship;
import org.ully.enterprise.WarpEngine;

/**
 * Calculates the ships movement for a single cycle.
 */
public class MovementCycle {

    private double time;

    /**
     * Create a cycle calculator for the given time interval.
     *
     * @param msec
     *            interval length in milliseconds
     */
    public MovementCycle(long msec) {
        time = msec / 1000.0;
    }

    /**
     * Calculates and performs the movement of the ship.
     *
     * @param ship
     */
    public void calculate(Starship ship) {
        ship.acceleration = 0;
        ship.engines().forEach(engine -> move(ship, engine));
    }

    /**
     * Add the impulse of an engine to the ships movement.
     *
     * @param engine
     */
    private void move(Starship ship, WarpEngine engine) {
        ship.acceleration += engine.getForce(ship.mass()).value() / ship.mass();
        ship.speed += ship.acceleration * time;
        ship.dist += ship.speed * time;
    }

}
