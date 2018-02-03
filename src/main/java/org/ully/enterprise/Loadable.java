package org.ully.enterprise;

import org.ully.enterprise.units.Energy;

/**
 * Interface for Components, that support energy retention.
 */
public interface Loadable {


    /**
     * Current energy load of the component.
     *
     * @return the energy currently stored.
     */
    Energy getLoad();

    /**
     * maximum energy load of the component.
     *
     * @return the maximum energy.
     */
    Energy getMaxLoad();

}
