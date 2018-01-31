package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

/**
 * Single defensive power shield.
 * <p>
 * loading characteristic is linear
 */
public class Shield implements Loadable, Consuming {

    private static final Energy MAX_LOAD = Energy.of(1000);
    private static final Energy LOADING_ENERGY = Energy.of(100);

    private Energy load = Energy.ZERO;

    public Energy getMaxLoad() {
        return MAX_LOAD;
    }

    @Override
    public Energy getLoad() {
        return load;
    }

    @Override
    public Energy getLoadingPower(long msec) {
        return !load.ge(MAX_LOAD) ? LOADING_ENERGY.scale(msec) : Energy.ZERO;
    }

    @Override
    public void load(Energy energy) {
        load = load.add(energy);
    }

    public void unload(Energy energy) {
        load = load.sub(energy);
    }

    @Override
    public Energy getPowerEntropy(long msec) {
        return Energy.of(1);
    }
}
