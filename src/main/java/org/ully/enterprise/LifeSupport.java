package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents the compound life support system.
 */
public class LifeSupport extends Component {

    Power max = Power.of(10);
    Power current = Power.ZERO;

    LifeSupport(String name) {
        super(name);
    }

    @Override
    public Power getCurrentPowerFlow() {
        return Power.of(5);
    }

    @Override
    public Power getMaxPower() {
        return max;
    }

    public Power getPower() {
        return current;
    }

    @Override
    public void load(Energy energy, long msec) {
        current = energy.toPower(msec);
    }
}
