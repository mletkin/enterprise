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

        public Direction revert() {
            return this == IN ? OUT : IN;
        }
    }

    /**
     * Create a component with the given name.
     *
     * @param name
     *            the name of the component.
     */
    protected Component(String name) {
        this.name = name;
    }

    /**
     * The name of the component.
     *
     * @return the name of the component
     */
    public String getName() {
        return name;
    }

    /**
     * Toggles the online state.
     */
    public void toggle() {
        online = !online;
    }

    /**
     * Is the component online?
     *
     * @return true iff the component is connected
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * The current flow direction.
     *
     * @return the current flow direction
     */
    public Direction getDirection() {
        return flowDirection;
    }

    /**
     * The power that currently flows between component and power system.
     *
     * @return the power currently flowing
     */
    public abstract Power getCurrentPowerFlow();

    /**
     * The Power that might flow currently.
     *
     * @return the anticipated power flow
     */
    public abstract Power getPotentialPowerFlow();

    /**
     * Maximum power that may flow between component and power system.
     *
     * @return the maximum power that migth flow
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
        return getPotentialPowerFlow().toEnergy(msec);
    };

    /**
     * Supply or consume the Energy through the connected circuit in the time
     * interval ahead.
     *
     * @param energy
     *            the energy amount to supply/consume
     * @param msec
     *            the length of the time interval in milliseconss
     */
    public abstract void load(Energy energy, long msec);

    /**
     * Draw energy from the component.
     *
     * @param power
     *            the power amount to drain
     * @param msec
     *            the length of the time interval in milliseconss
     */
    public void drain(Power power, long msec) {
        // does nothing by default
    };

    /**
     * Internal change during a time interval.
     *
     * @param msec
     *            the length of the time interval in milliseconss
     */
    public void internal(long msec) {
        // does nothing by default
    }

}
