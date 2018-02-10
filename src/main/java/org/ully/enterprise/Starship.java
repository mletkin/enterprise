package org.ully.enterprise;

import java.util.stream.Stream;

import org.ully.enterprise.energy.Circuit;
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

    private PowerFlowEmulator PowerFlowEmulator;
    private String name;

    public abstract Stream<Circuit> getCircuits();

    /**
     * Create a ship with the given name.
     *
     * @param name
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
     * Starts the energy flow emulation.
     */
    public void powerUp() {
        if (PowerFlowEmulator == null) {
            PowerFlowEmulator = PowerFlowEmulator.get().with(getCircuits());
        }
        PowerFlowEmulator.start();
    }

    /**
     * Stops the energy flow emulation.
     */
    public void powerDown() {
        if (PowerFlowEmulator != null) {
            PowerFlowEmulator.stop();
        }
    }
}
