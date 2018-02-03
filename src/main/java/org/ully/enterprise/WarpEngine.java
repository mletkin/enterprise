package org.ully.enterprise;

import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

public class WarpEngine extends Component implements Loadable {

    Power max = Power.of(10);
    Power current = Power.ZERO;

    WarpEngine(String name) {
        super(name);
        flowDirection = Direction.IN;
    }

    @Override
    public void load(Energy energy, long msec) {
        current = energy.toPower(msec);
    }

    @Override
    public Power getPowerInput() {
        return isOnline() ? max : Power.ZERO;
    }

    @Override
    public Energy getLoad() {
        return Energy.ZERO;
    }

    public Power getPower() {
        return current;
    }

    public Power getMax() {
        return max;
    }

    public void setMax(Power max) {
        this.max = max;
    }

    @Override
    public Power getFlow() {
        return getPowerInput();
    }

    @Override
    public void load(Power power, long msec) {
        load(power.toEnergy(msec), msec);
    }

}
