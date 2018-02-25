package org.ully.enterprise;

import static java.lang.Math.sqrt;

import org.ully.enterprise.units.Force;
import org.ully.enterprise.units.Power;
import org.ully.enterprise.util.Util;

/**
 * Machine arrangement in a starship.
 *
 * At the moment: A pair of two engines mounted symmetrically on the port and
 * starboard side of the ship. Both engines exert the same toruqe when emitting
 * the same power.<br>
 * A clockwise torque yields a positive angular acceleration.
 */
public class MachineAggregate<T extends Component & Engine> {

    private Starship ship;
    private T left;
    private T right;

    /**
     * Creates an aggregate for two engines of a star ship.
     *
     * @param ship
     *            the ship carrying the engines
     * @param left
     *            the port engine
     * @param right
     *            the starboard engine
     */
    public MachineAggregate(Starship ship, T left, T right) {
        this.ship = ship;
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the port engine of the aggregate.
     *
     * @return the port engine
     */
    public T left() {
        return left;
    }

    /**
     * Returns the starboard engine of the aggregate.
     *
     * @return the starboard engine
     */
    public T right() {
        return right;
    }

    /**
     * Returns the accceleration in direction of the ship's heading.
     *
     * @param delta
     *            time interval
     * @return the acceleration in m/s^2
     */
    public double getAcceleration(double delta) {
        return getForwardForce(delta).value() / ship.mass();
    }

    /**
     * Returns the angular accceleration on the ship's main axis.
     *
     * @param delta
     *            time interval
     * @return the acceleration in rad / s^2
     */
    public double getAngularAcceleration(double delta) {
        double power = left.getPower().value() - right.getPower().value();
        return Math.signum(power) * Math.sqrt(Math.abs(power) * delta / ship.angularMass());
    }

    /**
     * Returns the combined force thrusting the ship forward over a given time
     * interval.
     *
     * @param time
     *            time interval for the calculation in seconds
     * @return the combines force
     */
    public Force getForwardForce(double time) {
        Power power = Power.min(left.getPower(), right.getPower()).multi(2);
        double mass = ship.mass();
        return Util.isZero(time) ? Force.ZERO : Force.of(mass * sqrt(2 * power.value() / mass / time));
    }

}
