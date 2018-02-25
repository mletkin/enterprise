package org.ully.enterprise;

import org.ully.enterprise.units.Force;
import org.ully.enterprise.units.Power;

/**
 * Interface for thrust supplying engines.
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

    /**
     * The current output power of the engine.
     *
     * @return the output power
     */
    Power getPower();

    /**
     * Sets the required output power of the engine.
     *
     * @param wanted
     *            the required power output
     */
    void setWantedPower(Power wanted);

    /**
     * Returns the currently required output power of the engine.
     *
     * @return the required output power
     */
    Power getWantedPower();

}
