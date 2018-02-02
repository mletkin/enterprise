package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single phaser bank.
 */
public class Phaser extends Component implements Loadable {

    private static final Energy MAX_LOAD = Energy.of(100);

    private Energy load = Energy.ZERO;

    public Phaser(String name) {
        super(name);
    }

    public Energy getMaxLoad() {
        return MAX_LOAD;
    }

    @Override
    public Energy getLoad() {
        return load;
    }

    @Override
    public Power getPowerInput() {
        return isOnline() && !load.ge(MAX_LOAD) ? Power.of(100 / (10 + load.value())) : Power.ZERO;
    }

    @Override
    public void load(Energy energy, long msec) {
        load = load.add(energy);
    }

    /**
     * Fire phaser bank.
     */
    public void fire() {
        load = Energy.ZERO;
    }

}
