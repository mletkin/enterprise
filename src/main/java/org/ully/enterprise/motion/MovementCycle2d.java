package org.ully.enterprise.motion;

import org.ully.enterprise.MachineAggregate;
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
        ship.drives().forEach(engine -> move(ship, engine));
    }

    /**
     * Add the output of a single engine to the ship's movement.
     *
     * @param engine
     */
    private void move(Starship ship, MachineAggregate drive) {

        ship.angleAcceeration = drive.getAngularAcceleration(time);
        ship.spin += ship.angleAcceeration * time;
        ship.heading(ship.heading().rotate(ship.spin * time));

        ship.acceleration = drive.getAcceleration(time);
        ship.bearing(ship.bearing().add(ship.heading().multi(ship.acceleration * time)));
        ship.position(ship.position().add(ship.bearing().multi(time)));
    }

}
