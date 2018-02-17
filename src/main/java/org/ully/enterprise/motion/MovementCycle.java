package org.ully.enterprise.energy;

import org.ully.enterprise.Starship;
import org.ully.enterprise.WarpEngine;

/**
 * Calculates the ships movement for a single cycle.
 */
public class MovementCycle implements Cycle {

    private double time;

    @Override
    public MovementCycle withDelta(long msec) {
        this.time = msec / 1000.0;
        return this;
    }

    @Override
    public void calculateSingleCycle(Starship ship) {
        ship.acceleration = 0;
        ship.engines().forEach(engine -> move(ship, engine));
    }

    /**
     * Add the output of the engine to the ship's movement.
     *
     * @param engine
     */
    private void move(Starship ship, WarpEngine engine) {
        ship.acceleration += engine.getForce(ship.mass()).value() / ship.mass();
        ship.speed += ship.acceleration * time;
        ship.dist += ship.speed * time;
    }

}
