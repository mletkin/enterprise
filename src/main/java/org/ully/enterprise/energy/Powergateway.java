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

    Power power = Power.ZERO;

    /**
     * Creates a power exchange gateway with the given name.
     *
     * @param name
     *            name of the gateway
     */
    public PowerGateway(String name) {
        super(name);
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
        this.power = isOnline() ? power : Power.ZERO;
    }

    @Override
    public Power getMaxPower() {
        return power;
    }

    @Override
    public void load(Energy energy, long msec) {
        // TODO Auto-generated method stub
    }

    /**
     * Sets the direction of the energy flow.
     *
     * @param direction
     *            flow direction OUT means the circuit needs energy
     */
    public void setDirection(Direction direction) {
        flowDirection = direction;
    }
}
