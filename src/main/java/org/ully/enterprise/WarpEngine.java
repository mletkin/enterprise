package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single warp engine drive.
 */
public class WarpEngine extends Component {

    Power maxPower = Power.of(10);
    Power currentPower = Power.ZERO;
    Power wantedPower = Power.ZERO;
    Power potentialPower = Power.ZERO;

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
        return isOnline() ? potentialPower : Power.ZERO;
    }

    public Power setWantedPowerFlow(Power wantedPower) {
        return this.wantedPower = wantedPower;
    }

    public Power getWantedPowerFlow() {
        return wantedPower;
    }

    @Override
    public void internal(long msec) {
        double current = getCurrentPowerFlow().value();
        double wanted = getWantedPowerFlow().value();

        potentialPower = Power.of(current + (wanted - current) / 1000 * msec);
    }
}
