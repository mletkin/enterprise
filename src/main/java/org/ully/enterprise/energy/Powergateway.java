package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

public class Powergateway extends Component {

    Power power = Power.ZERO;

    protected Powergateway() {
        super("gateway");
    }

    @Override
    public Power getCurrentPowerFlow() {
        return power;
    }

    @Override
    public Power getPotentialPowerFlow() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    @Override
    public Power getMaxPower() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void load(Energy energy, long msec) {
        // TODO Auto-generated method stub
    }

    public void setDirection(Direction direction) {
        flowDirection = direction;
    }
}
