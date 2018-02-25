package org.ully.enterprise;

import static java.lang.Math.sqrt;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Force;
import org.ully.enterprise.units.Power;
import org.ully.enterprise.util.Util;

/**
 * Represents a single impulse engine drive.
 * <ul>
 * <li>The actual input power approaches the requested power with asymptotic latency
 * <li>The engine efficiency is 95%
 * </ul>
 */
public class ImpulseEngine extends Component implements Engine {

    private static final double EFFICENCY = 0.95;

    Power maxPower = Power.of(10);
    Power currentPower = Power.ZERO;
    Power wantedPower = Power.ZERO;
    Power potentialPower = Power.ZERO;

    public double time = 0;

    {
        type = "imp";
    }

    /**
     * Creates an impulse engine with the given name.
     *
     * @param name
     *            Name of the engine
     */
    public ImpulseEngine(String name) {
        super(name);
    }

    @Override
    public void load(Energy energy, long msec) {
        currentPower = energy.toPower(msec);
        time = msec / 1000.0;
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

    @Override
    public Power getPower() {
        return getCurrentPowerFlow().multi(EFFICENCY);
    }

    @Deprecated
    @Override
    public Force getForce(double mass) {
        return Util.isZero(time) ? Force.ZERO : Force.of(mass * sqrt(2 * currentPower.value() / mass / time));
    }

    @Override
    public void setWantedPower(Power wantedPower) {
        this.wantedPower = wantedPower;
    }

    @Override
    public Power getWantedPower() {
        return wantedPower;
    }

    @Override
    public void internal(long msec) {
        double current = getCurrentPowerFlow().value();
        double wanted = getWantedPower().value() / EFFICENCY;

        potentialPower = Power.of(current + (wanted - current) / 1000 * msec);
    }

}
