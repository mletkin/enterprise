package org.ully.enterprise.energy;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Represents a power circuit that connects a set of components.
 * <p>
 * The circuit itself acts as a component. This way a set of circuits can be
 * treated as a circuit to exchange power via {@code PowerGateway}-Objects.
 * <p>
 * The direction is to be viewed from outside the circuit.
 * <ul>
 * <li>OUT means, the circuit has surplus energy.
 * <li>IN means the circuit requires energy,
 * </ul>
 *
 */
public class Circuit extends Component {

    private Power potentialPower = null;
    private List<Component> consumer = new ArrayList<>();
    PowerGateway gateway = new PowerGateway();

    /**
     * Creates a power circuit with a power exchange gateway.
     */
    private Circuit() {
        super("");
        consumer.add(gateway);
    }

    /**
     * Creates a circuit that contains the components from the stream.
     *
     * @param components
     */
    public Circuit(Stream<Component> components) {
        this();
        ofNullable(components).orElse(Stream.empty()).forEach(consumer::add);
    }

    /**
     * Creates a circuit that contains the given components.
     *
     * @param c
     * @param cList
     */
    public Circuit(Component c, Component... cList) {
        this();
        consumer.add(c);
        if (cList != null) {
            Stream.of(cList).forEach(consumer::add);
        }
    }

    /**
     * Returns all power supplying components in the circuit.
     *
     * @return
     */
    public Stream<Component> getSupplier() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.OUT);
    }

    /**
     * Returnss all power consuming components in the circuit.
     *
     * @return
     */
    public Stream<Component> getConsumer() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.IN);
    }

    /**
     * Returns all components in the circuit.
     *
     * @return
     */
    public Stream<Component> getComponents() {
        return consumer.stream();
    }

    @Override
    public Power getPotentialPowerFlow() {
        // if (potentialPower == null) {
        calculate();
        // }
        return potentialPower;
    }

    @Override
    public Direction getDirection() {
        // if (direction == null) {
        calculate();
        // }
        return flowDirection;
    }

    public void setGatewayPower(Power power) {
        gateway.setPower(power);
    }

    @Override
    public Power getCurrentPowerFlow() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Power getMaxPower() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void load(Energy energy, long msec) {
        gateway.setPower(energy.toPower(msec));
        gateway.setDirection(flowDirection.revert());
    }

    /**
     * Calculate potential power flow.
     *
     * @param msec
     */
    public void calculate() {
        double flow = getComponents()//
                .map(this::flow) //
                .mapToDouble(Power::value)//
                .sum();
        potentialPower = Power.of(Math.abs(flow));
        flowDirection = flow > 0 ? Direction.OUT : Direction.IN;
    }

    private Power flow(Component comp) {
        return comp.getDirection() == Direction.IN ? comp.getPotentialPowerFlow().neg() : comp.getPotentialPowerFlow();
    }
}
