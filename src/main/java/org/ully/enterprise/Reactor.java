package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Representation of a single power supplying reactor unit.
 */
public class Reactor extends Component {

    private Power maxFlow;
    private Power currentFlow = Power.ZERO;
    private Power wantedFlow = Power.ZERO;

    /**
     * create a reator with given name and maximum power.
     *
     * @param name
     * @param maxPower
     */
    public Reactor(String name, Power maxPower) {
        super(name);
        flowDirection = Direction.OUT;
        maxFlow = maxPower;
    }

    @Override
    public Power getMaxPower() {
        return maxFlow;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return currentFlow;
    }

    @Override
    public Power getPotentialPowerFlow() {
        return getCurrentPowerFlow();
    }

    public void setFlow(Power flow) {
        this.currentFlow = flow;
    }

    public Power getWantedFlow() {
        return wantedFlow;
    }

    public void setWantedFlow(Power wantedFlow) {
        this.wantedFlow = wantedFlow;
    }

    @Override
    public void load(Energy energy, long msec) {
        // loading has no effect
    }

    @Override
    public void internal(long msec) {
        double current = getCurrentPowerFlow().value();
        double wanted = getWantedFlow().value();

        setFlow(Power.of(current + (wanted - current) / 1000 * msec));
    }
}
