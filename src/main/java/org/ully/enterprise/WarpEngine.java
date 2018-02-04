package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single warp engine drive.
 */
public class WarpEngine extends Component {

    Power maxPower = Power.of(10);
    Power currentPower = Power.ZERO;

    /**
     * create a warp engine with the given name.
     *
     * @param name
     */
    WarpEngine(String name) {
        super(name);
    }

    @Override
    public void load(Energy energy, long msec) {
        currentPower = energy.toPower(msec);
    }

    @Deprecated
    public Power getPower() {
        return getCurrentPowerFlow();
    }

    @Override
    public Power getMaxPower() {
        return maxPower;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return currentPower;
    }

    @Override
    public Power getPotentialPowerFlow() {
        return isOnline() ? maxPower : Power.ZERO;
    }

}
