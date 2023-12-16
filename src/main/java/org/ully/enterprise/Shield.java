package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single defensive power shield.
 * <p>
 * loading characteristic is linear
 */
public class Shield extends Component implements Loadable {

    private static final Power ENTROPY = Power.of(1);
    public static final Energy MAX_LOAD = Energy.of(100);
    private static final Power LOADING_POWER = Power.of(10);
    private static final Power UNLOADING_POWER = Power.of(5);

    private Energy load = Energy.ZERO;
    private Power currentFlow = Power.ZERO;
    private Energy maxLoad = MAX_LOAD;

    {
        type = "shield";
    }

    /**
     * Creates a shield with the given name.
     *
     * @param name
     *                 name of the shield
     */
    public Shield(String name) {
        super(name);
    }

    @Override
    public Energy getMaxLoad() {
        return maxLoad;
    }

    @Override
    public void setMaxLoad(Energy maxLoad) {
        this.maxLoad = Energy.min(MAX_LOAD, maxLoad);
    }

    @Override
    public Energy getLoad() {
        return load;
    }

    @Override
    public void load(Energy energy, long msec) {
        currentFlow = energy.toPower(msec);
        if (flowDirection == Direction.IN) {
            load = load.add(energy);
        } else {
            load = load.sub(energy);
        }
        load = load.le(Energy.ZERO) ? Energy.ZERO : load;
    }

    private Power getEntropy() {
        return ENTROPY;
    }

    public void setDirection(Direction direction) {
        flowDirection = direction;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return currentFlow;
    }

    @Override
    public Power getPotentialPowerFlow() {
        if (!isOnline()) {
            return Power.ZERO;
        }

        if (flowDirection == Direction.IN) {
            return maxLoad.ge(load) ? LOADING_POWER : getEntropy();
        }
        return load.le(Energy.ZERO) ? Power.ZERO : UNLOADING_POWER;
    }

    @Override
    public Power getMaxPower() {
        return getDirection() == Direction.IN ? LOADING_POWER : UNLOADING_POWER;
    }

    @Override
    public void drain(Power power, long msec) {
        load = load.sub(power.toEnergy(msec));
    }

    @Override
    public void internal(long msec) {
        if (!load.equals(Energy.ZERO)) {
            load = load.sub(getEntropy().toEnergy(msec));
        }
    }
}
