package org.ully.enterprise;

import org.ully.enterprise.units.Power;

public abstract class Component {

    private String name;
    private boolean online = true;
    protected Direction flowDirection = Direction.IN;

    public enum Direction {
        IN, OUT;
    }

    Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void toggle() {
        online = !online;
    }

    public boolean isOnline() {
        return online;
    }

    public Direction getDirection() {
        return flowDirection;
    }

    /**
     * Power that currently flows between component and power system.
     *
     * @return
     */
    public abstract Power getCurrentPowerFlow();

    public abstract void load(Power power, long msec);

}
