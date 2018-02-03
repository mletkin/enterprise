package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single warp engine drive.
 */
public class WarpEngine extends Component {

    Power max = Power.of(10);
    Power current = Power.ZERO;

    WarpEngine(String name) {
        super(name);
    }

    @Override
    public void load(Energy energy, long msec) {
        current = energy.toPower(msec);
    }

    public Power getPower() {
        return current;
    }

    @Override
    public Power getMaxPower() {
        return max;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return isOnline() ? max : Power.ZERO;
    }

}
