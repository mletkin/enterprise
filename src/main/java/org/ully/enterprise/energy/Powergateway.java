package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Power exchange gateway.
 * <p>
 * A component attached to a circuit to reflect the amount of energy the circuit
 * requires or provides for or from other circuits. Acts as a gateway to export
 * the surplus or to import required energy. circuits.
 * <p>
 * The direction is to be viewed from the circuit the gateway belongs to.
 * <ul>
 * <li>OUT means, the circuit _requires_ energy, because the gateway acts as a
 * supplier.
 * <li>IN means the circuit has energy surplus, because the gateway acts as a
 * consumer in the circuit.
 * </ul>
 */
public class PowerGateway extends Component {

    /**
     * Creates a power exchange gateway with the given name.
     *
     * @param name
     */
    public PowerGateway(String name) {
        super(name);
    }

    Power power = Power.ZERO;

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
