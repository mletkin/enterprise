package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single defensive power shield.
 * <p>
 * loading characteristic is linear
 */
public class Shield extends Component implements Loadable {

    private static final Energy MAX_LOAD = Energy.of(100);
    private static final Power LOADING_POWER = Power.of(10);
    private static final Power UNLOADING_POWER = Power.of(5);

    private Energy load = Energy.ZERO;

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
        if (flowDirection == Direction.IN) {
            load = load.add(energy);
        } else {
            load = load.sub(energy);
        }
        load = load.sub(getEntropy().toEnergy(msec));
        load = load.le(Energy.ZERO) ? Energy.ZERO : load;
    }

    private Power getEntropy() {
        return Power.of(1);
    }

    public void setDirection(Direction direction) {
        flowDirection = direction;
    }

    @Override
    public Power getCurrentPowerFlow() {
        if (!isOnline()) {
            return Power.ZERO;
        }

        if (flowDirection == Direction.IN) {
            return !load.ge(MAX_LOAD) ? LOADING_POWER : getEntropy();
        }
        return load.le(Energy.ZERO) ? Power.ZERO : UNLOADING_POWER;
    }

    @Override
    public Power getMaxPower() {
        return LOADING_POWER;
    }

}
