package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

/**
 * Interface for Components, that support energy retention.
 */
public interface Loadable {

    /**
     * Returns the current energy load of the component.
     *
     * @return the energy currently stored.
     */
    Energy getLoad();

    /**
     * Returns the maximum energy load of the component.
     *
     * @return the maximum capacity.
     */
    Energy getMaxLoad();

}
