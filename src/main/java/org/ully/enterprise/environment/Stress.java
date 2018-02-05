package org.ully.enterprise.environment;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Power;

/**
 * Represents stress on a ships component like energy drain.
 */
public class Stress {

    Component component;
    long duration = 0;
    Power power;

    public Stress(Component component) {
        this.component = component;
    }

    public void setMilliseconds(long msec) {
        this.duration = msec;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    void apply(long duration) {
        component.drain(power, duration);
        this.duration -= duration;
    }

    boolean isActive() {
        return duration > 0;
    }
}
