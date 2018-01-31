package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

/**
 * The (one) power reactor
 */
public class Reactor {

    private static final Energy MAX = Energy.of(10);
    private Energy flow = Energy.of(1);

    public Reactor(Energy maxEnergy) {
        flow = maxEnergy;
    }

    /**
     * Maximum energy that may flow per second.
     *
     * @return
     */
    public Energy maxFlow(long msec) {
        return flow.scale(msec);
    }

    public Energy getFlow(long msec) {
        return flow.scale(msec);
    }

    public void setFlow(Energy flow) {
        this.flow = flow;
    }
}
