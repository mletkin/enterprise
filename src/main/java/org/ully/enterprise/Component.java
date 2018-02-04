package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Base class for all energy powered ship components.
 * <p>
 * By default a component is consuming only.<br>
 */
public abstract class Component {

    private String name;
    private boolean online = true;
    protected Direction flowDirection = Direction.IN;

    public enum Direction {
        IN, OUT;
    }

    /**
     * create a component with the given name.
     *
     * @param name
     */
    protected Component(String name) {
        this.name = name;
    }

    /**
     * get the name of the component.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Toggle online state.
     */
    public void toggle() {
        online = !online;
    }

    /**
     * is the component online?
     *
     * @return
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Returns the current flow direction.
     *
     * @return the current flow direction
     */
    public Direction getDirection() {
        return flowDirection;
    }

    /**
     * Power that currently flows between component and power system.
     *
     * @return
     */
    public abstract Power getCurrentPowerFlow();

    /**
     * Maximum power that may flow between component and power system.
     *
     * @return
     */
    public abstract Power getMaxPower();

    /**
     * The Energy that might flow in the time interval ahead.
     *
     * @param msec
     *            the next milliseconds to consider.
     * @return the anticipated energy amount
     */
    public Energy getPotentialEnergyFlow(long msec) {
        return getCurrentPowerFlow().toEnergy(msec);
    };

    /**
     * Supply or consume the Energy in the time interval ahead.
     *
     * @param energy
     *            the energy amunt to supply/consume
     * @param msec
     *            the length of the time interval in milliseconss
     */
    public abstract void load(Energy energy, long msec);

}
