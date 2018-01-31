package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

/**
 * Represents a single phaser bank.
 */
public class Phaser implements Loadable {

    private static final Energy MAX_LOAD = Energy.of(100);

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
        return !load.ge(MAX_LOAD) ? loadingEnergy(msec) : Energy.ZERO;
    }

    private Energy loadingEnergy(long msec) {
        return Energy.of(100 / (1 + load.value())).scale(msec);
    }

    @Override
    public void load(Energy energy) {
        load = load.add(energy);
    }

    /**
     * Fire phaser bank.
     */
    public void fire() {
        load = Energy.ZERO;
    }

}
