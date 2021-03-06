package org.ully.enterprise.motion;

import org.ully.enterprise.Engine;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Cycle;

/**
 * Calculates the ships movement for a single cycle.
 * <p>
 * Motion is one-dimesional, acceleration, speed and distance are scalar.
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
     * Add the output of the engine to the ship's bearing.
     *
     * @param engine
     */
    private void move(Starship ship, Engine engine) {
        double engineAcceleration = engine.getForce(ship.mass()).value() / ship.mass();
        ship.acceleration += engineAcceleration;
        ship.speed += engineAcceleration * time;
        ship.dist += ship.speed * time;
    }

}
