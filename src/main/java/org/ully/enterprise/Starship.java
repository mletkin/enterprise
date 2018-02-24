package org.ully.enterprise;

import java.util.stream.Stream;

import org.ully.enterprise.energy.Checker;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.Emulator;
import org.ully.enterprise.energy.PowerCycle;
import org.ully.enterprise.motion.MovementCycle2d;
import org.ully.enterprise.units.Vector;

/**
 * Configuration for a galaxy class starship.
 *
 * <ul>
 * <li>The starship contains power consuming and supplying components.
 * <li>The components are connected through (energy) circuits.
 * <li>The circuits are seperated and not connected.
 * <li>A single poewer emulator emulates the energy flow within all the
 * circuits.
 * </ul>
 */
public abstract class Starship {

    private Emulator powerFlowEmulator;
    private Emulator movementEmulator;

    private String name;

    // single dimensional bearing parameters (temporary)
    public double acceleration = 0;
    public double speed = 0;
    public double dist = 0;

    // multi dimensional movement
    private Vector bearing = Vector.ZERO;
    private Vector heading = Vector.of(0,1);
    private Vector position = Vector.ZERO;

    public double angleAcceeration = 0;
    public double spin = 0;

    public void stop() {
        bearing = Vector.ZERO;
        speed = 0;
        spin = 0;
    }

    /**
     * Creates a ship with the given name.
     *
     * @param name
     *            name of the starship
     */
    public Starship(String name) {
        this.name = name;
    }

    /**
     * returns a vector indicating direction and speed of the ship's movement.
     *
     * @return
     */
    public Vector bearing() {
        return bearing;
    }

    public void bearing(Vector bearing) {
        this.bearing = bearing;
    }

    /**
     * Retuns a vector indicating the direction in which where the ship's stern
     * points, usually normalized.
     *
     * @return
     */
    public Vector heading() {
        return heading;
    }

    public void heading(Vector heading) {
        this.heading = heading;
    }

    /**
     * Returns a vector indicatining the ship's position.
     *
     * @return
     */
    public Vector position() {
        return position;
    }

    public void position(Vector position) {
        this.position = position;
    }

    /**
     * speed is the absolute value of the bearing vector.
     *
     * @return
     */
    public double speed() {
        return bearing.abs();
    }

    /**
     * Returns the complete power system.
     *
     * @return the circuit containing the whole power systeem
     */
    public abstract Circuit powerSystem();

    /**
     * Returns all the ship's engines.
     *
     * @return a Stream with all WarpEngine components
     */
    public Stream<Engine> engines() {
        return powerSystem().getAllComponents() //
                .filter(c -> Engine.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Engine) c);
    }

    public Stream<MachineAggregate> drives() {
        return Stream.empty();
    }

    /**
     * Returns the name of the ship.
     *
     * @return
     */
    String getName() {
        return name;
    }

    /**
     * Returns the mass of the starship
     *
     * @return the starship's mass
     */
    public abstract double mass();

    /**
     * Returns the ships moment of inertia.
     *
     * @return the ships moment of inertia
     */
    public abstract double angularMass();

    /**
     * Starts the emulation.
     * <p>
     * No emulation with a corrupted power system-
     */
    public void powerUp() {
        new Checker().check(powerSystem());

        if (powerFlowEmulator == null) {
            powerFlowEmulator = Emulator.get().with(this).with(new PowerCycle());
        }
        if (movementEmulator == null) {
            movementEmulator = Emulator.get().with(this).with(new MovementCycle2d());
        }

        powerFlowEmulator.start();
        movementEmulator.start();
    }

    /**
     * Stops the emulation.
     */
    public void powerDown() {
        if (powerFlowEmulator != null) {
            powerFlowEmulator.stop();
        }

        if (movementEmulator != null) {
            movementEmulator.stop();
        }
    }
}
