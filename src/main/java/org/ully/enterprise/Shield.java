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
    private static final Energy MAX_LOAD = Energy.of(100);
    private static final Power LOADING_POWER = Power.of(10);
    private static final Power UNLOADING_POWER = Power.of(5);

    private Energy load = Energy.ZERO;
    private Power currentFlow = Power.ZERO;

    /**
     * create a shield with the given name.
     *
     * @param name
     */
    public Shield(String name) {
        super(name);
    }

    @Override
    public Energy getMaxLoad() {
        return MAX_LOAD;
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
            return MAX_LOAD.ge(load) ? LOADING_POWER : getEntropy();
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
        load = load.sub(getEntropy().toEnergy(msec));
    }
}
