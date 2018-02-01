package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

public interface Loadable {

    /**
     * Add/sub energy to object's load.
     *
     * @param energy
     */
    void load(Energy energy, long msec);

    default void load(Power power, long msec) {
        load(power.toEnergy(msec), msec);
    }

    /**
     * Energy to load in the given time unit.
     *
     * @return
     */
    default Energy getLoadingPower(long msec) {
        return getPowerInput().toEnergy(msec);
    }

    /**
     * Energy to load in the given time unit.
     *
     * @return
     */
    Power getPowerInput();

    /**
     * Current energy load of object.
     *
     * @return
     */
    Energy getLoad();

}
