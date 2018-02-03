package org.ully.enterprise;

import org.ully.enterprise.units.Power;

public class LifeSupport extends Component {

    Power max = Power.of(10);
    Power current = Power.ZERO;

    LifeSupport(String name) {
        super(name);
    }

    @Override
    public Power getFlow() {
        return Power.of(5);
    }

    @Override
    public void load(Power power, long msec) {
        current = power;
    }

    public Power getMax() {
        return max;
    }

    public Power getPower() {
        return current;
    }
}
