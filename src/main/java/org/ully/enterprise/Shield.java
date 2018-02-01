package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Single defensive power shield.
 * <p>
 * loading characteristic is linear
 */
public class Shield implements Loadable, Consuming {

    private static final Energy MAX_LOAD = Energy.of(100);
    private static final Power LOADING_POWER = Power.of(10);

    private Energy load = Energy.ZERO;

    public Energy getMaxLoad() {
        return MAX_LOAD;
    }

    @Override
    public Energy getLoad() {
        return load;
    }

    @Override
    public Power getPowerInput() {
        return !load.ge(MAX_LOAD) ? LOADING_POWER : Power.ZERO;
    }

    @Override
    public void load(Energy energy, long msec) {
        load = load.add(energy).sub(getPowerEntropy(msec));
        load = load.le(Energy.ZERO) ? Energy.ZERO : load;
    }

    @Override
    public Energy getPowerEntropy(long msec) {
        return Energy.of(1).scale(msec);
    }

}
