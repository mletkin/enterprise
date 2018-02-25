package org.ully.enterprise;

import static java.lang.Math.sqrt;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Force;
import org.ully.enterprise.units.Power;
import org.ully.enterprise.util.Util;

/**
 * Represents a single warp engine drive.
 * <ul>
 * <li>Actually it acts more like an impulse engine.
 * <li>The actual value approaches the wanted value with asymptotic latency
 * </ul>
 */
public abstract class WarpEngine extends Component implements Engine {

    private static final double WARP_FACTOR = 2; // == Power / Warp

    Power maxPower = Power.of(10);
    Power currentPower = Power.ZERO;
    Power wantedPower = Power.ZERO;
    Power potentialPower = Power.ZERO;

    public double time = 0;

    {
        type = "warp";
    }

    /**
     * Creates a warp engine with the given name.
     *
     * @param name
     *            Name of the engine
     */
    public WarpEngine(String name) {
        super(name);
    }

    @Override
    public void load(Energy energy, long msec) {
        currentPower = energy.toPower(msec);
        time = msec / 1000.0;
    }

    @Override
    public Power getPower() {
        return getCurrentPowerFlow();
    }

    @Override
    public Force getForce(double mass) {
        return Util.isZero(time) ? Force.ZERO : Force.of(mass * sqrt(2 * currentPower.value() / mass / time));
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

    public double getWantedWarp() {
        return getWantedPowerFlow().value() / WARP_FACTOR;
    }

    public void setWantedWarp(double warp) {
        setWantedPowerFlow(Power.of(warp * WARP_FACTOR));
    }

    public double getCurrentWarp() {
        return getCurrentPowerFlow().value() / WARP_FACTOR;
    }

}
