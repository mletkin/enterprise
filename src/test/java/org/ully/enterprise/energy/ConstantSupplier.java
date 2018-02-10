package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Ideal power source for testing purposes.
 * <p>
 * The load accumulates the energy supplied.
 */
public class ConstantSupplier extends Component {

    private Power currentFlow = Power.ZERO;
    private Energy load = Energy.ZERO;

    /**
     * create a supplier with given power output.
     *
     * @param power
     */
    public ConstantSupplier(Power power) {
        super("");
        flowDirection = Direction.OUT;
        currentFlow = power;
    }

    @Override
    public Power getMaxPower() {
        return currentFlow;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return currentFlow;
    }

    @Override
    public Power getPotentialPowerFlow() {
        return getCurrentPowerFlow();
    }

    @Override
    public void load(Energy energy, long msec) {
        load = load.add(energy);
    }

}
