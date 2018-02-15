package org.ully.enterprise;

import java.util.stream.Stream;

import org.ully.enterprise.energy.Checker;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.MovementEmulator;
import org.ully.enterprise.energy.PowerFlowEmulator;

/**
 * Configuration for a galaxy class starship.
 *
 * <ul>
 * <li>The starship contains power consuming and supplying components.
 * <li>The components are connected through (energy) circuits.
 * <li>The circuits are seperated and not connected.
 * <li>A single poewer emulator emulates he energy flow within all the circuits.
 * </ul>
 */
public abstract class Starship {

    private PowerFlowEmulator powerFlowEmulator;
    private MovementEmulator movementEmulator;

    private String name;

    // movement parameters (temporary)
    public double acceleration = 0;
    public double speed = 0;
    public double dist = 0;

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
    public Stream<WarpEngine> engines() {
        return powerSystem().getAllComponents() //
                .filter(c -> WarpEngine.class.isAssignableFrom(c.getClass())) //
                .map(c -> (WarpEngine) c);
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
     * Returns the name of the ship.
     *
     * @return
     */
    String getName() {
        return name;
    }

    /**
     * The total mass of the straship
     *
     * @return
     */
    public double mass() {
        return 0;
    }

    /**
     * Starts the emulation.
     * <p>
     * No emulation with a corrupted power system-
     */
    public void powerUp() {
        if (powerFlowEmulator == null) {
            new Checker().check(powerSystem());
            powerFlowEmulator = PowerFlowEmulator.get().with(this);
        }
        if (movementEmulator == null) {
            movementEmulator = MovementEmulator.get().with(this);
        }

        powerFlowEmulator.start();
        movementEmulator.start();
    }

    /**
     * Stops emulation.
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
