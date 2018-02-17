package org.ully.enterprise;

import org.ully.enterprise.units.Force;

/**
 * Interface for all kinds of thrust supplying engines.
 */
public interface Engine {

    /**
     * Calculates the force output from the current power flow.
     *
     * @param mass
     *            the mass of the starship to be accelerated.
     * @return the calculated force
     */
    Force getForce(double mass);

}
