package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

public interface Consuming {

    /**
     * Energy to be consumed by external action in the given time unit.
     *
     * @return
     */
    default Energy getPowerOutput(long msec) {
        return Energy.ZERO;
    }
    /**
     * Maintaining Energy to be consumed in the given time unit.
     *
     * @return
     */
    default Energy getPowerEntropy(long msec) {
        return Energy.ZERO;
    }
}
