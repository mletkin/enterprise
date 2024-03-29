package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a single phaser bank.
 */
public class Phaser extends Component implements Loadable {

    private static final Power MAX_OUT_POWER = Power.of(5);
    private static final Power MAX_IN_POWER = Power.of(10);

    public static final Energy MAX_LOAD = Energy.of(100);

    private Energy load = Energy.ZERO;
    private Power currentFlow = Power.ZERO;
    private Energy maxLoad = MAX_LOAD;

    {
        type = "phaser";
    }

    /**
     * Creates a phaser bank with the given name.
     *
     * @param name
     *                 name of the phaser bank
     */
    public Phaser(String name) {
        super(name);
    }

    @Override
    public Energy getMaxLoad() {
        return MAX_LOAD;
    }

    @Override
    public void setMaxLoad(Energy maxLoad) {
        this.maxLoad = Energy.min(MAX_LOAD, maxLoad);
    }

    @Override
    public Energy getLoad() {
        return load;
    }

    @Override
    public void load(Energy energy, long msec) {
        currentFlow = energy.toPower(msec);
        if (flowDirection == Direction.IN) {
            load = load.add(energy);
        } else {
            load = load.sub(energy);
        }
    }

    /**
     * Fire phaser bank.
     */
    public void fire() {
        load = Energy.ZERO;
    }

    /**
     * Sets the direction in which the energy flows currently.
     *
     * @param direction
     *                      flow direction
     */
    public void setDirection(Direction direction) {
        flowDirection = direction;
    }

    @Override
    public Power getCurrentPowerFlow() {
        return currentFlow;
    }

    @Override
    public Power getPotentialPowerFlow() {
        if (!isOnline()) {
            return Power.ZERO;
        }

        if (flowDirection == Direction.IN) {
            return !load.ge(maxLoad) ? Power.of(100 / (10 + load.value())) : Power.ZERO;
        }
        return load.ge(Energy.ZERO) ? MAX_OUT_POWER : Power.ZERO;
    }

    @Override
    public Power getMaxPower() {
        return flowDirection == Direction.IN ? MAX_IN_POWER : MAX_OUT_POWER;
    }

    @Override
    public void drain(Power power, long msec) {
        load.sub(power.toEnergy(msec));
    }

}
