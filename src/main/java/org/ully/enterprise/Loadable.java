package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

public interface Loadable {

    /**
     * Add/sub energy to object's load.
     *
     * @param energy
     */
    void load(Energy energy);

    /**
     * Energy to load in the given time unit.
     *
     * @return
     */
    Energy getLoadingPower(long msec);

    /**
     * Current energy load of object.
     *
     * @return
     */
    Energy getLoad();

}
