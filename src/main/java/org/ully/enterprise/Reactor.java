package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Representation of a single power supplying reactor unit.
 */
public class Reactor extends Component {

    private Power maxFlow = Power.of(20);
    private Power currentFlow = Power.ZERO;
    private Power wantedFlow = Power.ZERO;

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
        // loading ha sno effect
    }

}
