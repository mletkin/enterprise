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
     * Creates a reator with given name and maximum power.
     *
     * @param name
     *            name of the reactor
     * @param maxPower
     *            maxumum power of the reactor
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

    private void setCurrentPowerFlow(Power flow) {
        this.currentFlow = flow;
    }

    /**
     * Returns currently requested power flow.
     *
     * @return the requested power flow
     */
    public Power getWantedFlow() {
        return wantedFlow;
    }

    /**
     * Sets currently requested power flow.
     *
     * @param wantedFlow
     *            the power flow to set
     */
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

        setCurrentPowerFlow(Power.of(current + (wanted - current) / 1000 * msec));
    }
}
