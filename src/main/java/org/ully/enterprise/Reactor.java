package org.ully.enterprise;

import org.ully.enterprise.units.Power;

/**
 * The (one) power pwrMain
 */
public class Reactor {

    private Power maxFlow = Power.of(20);
    private Power currentFlow = Power.ZERO;
    private Power wantedFlow = Power.ZERO;

    public Reactor(Power maxPower) {
        maxFlow = maxPower;
    }

    /**
     * Maximum energy that may currentFlow per second.
     *
     * @return
     */
    public Power maxFlow() {
        return maxFlow;
    }

    public Power getFlow() {
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

}
