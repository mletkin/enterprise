package org.ully.enterprise.energy;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;
import org.ully.enterprise.util.Util;

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
    private List<Component> components = new ArrayList<>();
    private PowerGateway gateway;

    /**
     * Creates a power circuit with a power exchange gateway.
     *
     * @param name
     *            the name of the circuit
     */
    public Circuit(String name) {
        super(name);
        gateway = new PowerGateway(getName());
        components.add(gateway);
    }

    /**
     * Adds a stream of circuits to the circuit.
     *
     * @param components
     *            list of circuits to add
     * @return the Circuit
     */
    public Circuit with(List<Circuit> components) {
        Util.streamOf(components).forEach(this.components::add);
        return this;
    }

    /**
     * Adds a list of components to the circuit.
     *
     * @param components
     *            list of comonents to add
     * @return the Circuit
     */
    public Circuit with(Component... components) {
        Stream.of(ofNullable(components).orElse(new Component[] {})).forEach(this.components::add);
        return this;
    }

    /**
     * Returns all power supplying components in the circuit.
     *
     * @return a stream of all power supplier
     */
    public Stream<Component> getSupplier() {
        return components.stream().filter(s -> s.getDirection() == Component.Direction.OUT);
    }

    /**
     * Returnss all power consuming components in the circuit.
     *
     * @return a stream of all power consumer
     */
    public Stream<Component> getConsumer() {
        return components.stream().filter(s -> s.getDirection() == Component.Direction.IN);
    }

    /**
     * Returns all subcircuits in the circuit.
     *
     * @return a stream of all circuits
     */
    Stream<Circuit> getSubCircuits() {
        return components.stream() //
                .filter(c -> Circuit.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Circuit) c);
    }

    /**
     * Returns all components in the circuit.
     *
     * @return a stream of all components
     */
    public Stream<Component> getComponents() {
        return components.stream();
    }

    @Override
    public Power getPotentialPowerFlow() {
        return potentialPower;
    }

    @Override
    public Direction getDirection() {
        return flowDirection;
    }

    /**
     * Sets the power flow of the gateway and all subgateways to zero.
     */
    public void resetGateway() {
        getSubCircuits().forEach(Circuit::resetGateway);
        gateway.load(Energy.ZERO, 1);
    }

    @Override
    public Power getCurrentPowerFlow() {
        return null;
    }

    @Override
    public Power getMaxPower() {
        return null;
    }

    @Override
    public void load(Energy energy, long msec) {
        gateway.load(energy, msec);
        gateway.setDirection(flowDirection.revert());
    }

    /**
     * Calculate the potential power flow.
     */
    public void calculate() {
        getSubCircuits().forEach(Circuit::calculate);
        double flow = gateway.isOnline() //
                ? getComponents()//
                        .map(this::flow) //
                        .mapToDouble(Power::value)//
                        .sum() //
                : 0;

        potentialPower = Power.of(Math.abs(flow));
        flowDirection = flow > 0 ? Direction.OUT : Direction.IN;
    }

    /**
     * Access to the power exchange gateway.
     *
     * @return the gateway for the circuit
     */
    public PowerGateway getPowerGateway() {
        return gateway;
    }

    private Power flow(Component comp) {
        return comp.getDirection() == Direction.IN ? comp.getPotentialPowerFlow().neg() : comp.getPotentialPowerFlow();
    }

    /**
     * Returns a stream with all circuits contained in the circuit.
     *
     * @return a stream with all circuits
     */
    public Stream<Circuit> getAllCircuits() {
        return Stream.concat(Stream.of(this), getSubCircuits().flatMap(Circuit::getAllCircuits));
    }

    /**
     * Returns a stream with all components and subcomponents.
     *
     * @return a stream with all components
     */
    public Stream<Component> getAllComponents() {
        return Stream.concat(getComponents(), getSubCircuits().flatMap(Circuit::getAllComponents));
    }

}
