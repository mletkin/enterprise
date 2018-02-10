package org.ully.enterprise.energy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * Power circuit, connecting a list of components.
 */
public class Circuit extends Component {

    private Power potentialPower = null;
    private List<Component> consumer = new ArrayList<>();
    Powergateway gateway = new Powergateway();

    /**
     * Creates a circuit containing the given Components.
     *
     * @param components
     */
    public Circuit(Component... components) {
        super("");
        if (components != null) {
            Stream.of(components).forEach(consumer::add);
            consumer.add(gateway);
        }
    }

    /**
     * Gets all power supplying components in teh circuit.
     *
     * @return
     */
    public Stream<Component> getSupplier() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.OUT);
    }

    /**
     * Gets all power consuming components in teh circuit.
     *
     * @return
     */
    public Stream<Component> getConsumer() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.IN);
    }

    /**
     * Gets all components in teh circuit.
     *
     * @return
     */
    public Stream<Component> getComponents() {
        return consumer.stream();
    }

    @Override
    public Power getPotentialPowerFlow() {
//        if (potentialPower == null) {
            calculate();
//        }
        return potentialPower;
    }

    @Override
    public Direction getDirection() {
//        if (direction == null) {
            calculate();
//        }
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
     * Calculate potention flow.
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
