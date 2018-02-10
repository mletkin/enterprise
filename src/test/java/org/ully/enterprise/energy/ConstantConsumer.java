package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Ideal consumer with constant energy consumption for testing purposes.
 */
public class ConstantConsumer extends Component {

    Power currentpower = Power.ZERO;
    Power requestedPower = Power.ZERO;
    Energy load = Energy.ZERO;

    /**
     * Creates a consumer with the given power requirement.
     *
     * @param powerRequested
     */
    protected ConstantConsumer(Power powerRequested) {
        super("");
        this.requestedPower = powerRequested;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return currentpower;
    }

    @Override
    public Power getPotentialPowerFlow() {
        return requestedPower;
    }

    @Override
    public Power getMaxPower() {
        return requestedPower;
    }

    @Override
    public void load(Energy energy, long msec) {
        currentpower = energy.toPower(msec);
        load = load.add(energy);
    }

    /**
     * Returns the energy consumed so far.
     *
     * @return
     */
    public Energy getLoad() {
        return load;
    }
}
