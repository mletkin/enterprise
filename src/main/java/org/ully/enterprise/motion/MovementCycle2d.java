package org.ully.enterprise.motion;

import org.ully.enterprise.Engine;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Cycle;

/**
 * Calculates the ships movement for a single cycle in a 2D pane.
 * <p>
 * Engines supply only thrust, no torque.
 */
public class MovementCycle2d implements Cycle {

    private double time;

    @Override
    public MovementCycle2d withDelta(long msec) {
        this.time = msec / 1000.0;
        return this;
    }

    @Override
    public void calculateSingleCycle(Starship ship) {
        ship.acceleration = 0;
        ship.engines().forEach(engine -> move(ship, engine));
    }

    /**
     * Add the output of a single engine to the ship's movement.
     *
     * @param engine
     */
    private void move(Starship ship, Engine engine) {
        double engineAcceleration = engine.getForce(ship.mass()).value() / ship.mass();

        ship.acceleration += engineAcceleration;
        ship.bearing(ship.bearing().add(ship.heading().multi(engineAcceleration * time)));
        ship.position(ship.position().add(ship.bearing().multi(time)));
    }

}
