package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents the compound life support system.
 */
public class LifeSupport extends Component {

    Power maxPower = Power.of(10);
    Power neededPower = Power.of(5);
    Power current = Power.ZERO;

    /**
     * Create a life support system with the given name.
     *
     * @param name of the component
     */
    LifeSupport(String name) {
        super(name);
    }

    @Override
    public Power getCurrentPowerFlow() {
        return current;
    }

    @Override
    public Power getPotentialPowerFlow() {
        return neededPower;
    }

    @Override
    public Power getMaxPower() {
        return maxPower;
    }

    @Override
    public void load(Energy energy, long msec) {
        current = energy.toPower(msec);
    }

}
