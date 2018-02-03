package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.Power;

/**
 * What happens within a single loading/discharge cycle for a circuit.
 * <p>
 * Emulates the energy flow within a single time unit.<p>
 * FIXME: components may switch flow direction while calculating
 */
public class Cycle {

    private static final double EPSILON = 0.00000000001;
    private Circuit circuit;

    public Cycle(Circuit circuit) {
        this.circuit = circuit;
    }

    /**
     * Move energy from suppliers to consumers.
     */
    public void calculate(long msec) {

        double available = energySupplied(msec);
        double required = energyRequired(msec);
        double quotient = getQuotient(required, available);

        circuit.getConsumer().forEach(s -> this.supplyEnergy(s, quotient, msec));
        circuit.getSupplier().forEach(s -> this.consumeEnergy(s, quotient, msec));

        circuit.getReactors().forEach(r -> adjustReactor(r, msec));
    }

    private double energySupplied(long msec) {
        return circuit.getSupplier()//
                .map(Component::getCurrentPowerFlow)//
                .map(p -> p.toEnergy(msec))//
                .mapToDouble(Energy::value)//
                .sum();
    }

    private double energyRequired(long msec) {
        return circuit.getConsumer()//
                .map(Component::getCurrentPowerFlow) //
                .map(p -> p.toEnergy(msec))//
                .mapToDouble(Energy::value)//
                .sum();
    }

    private void adjustReactor(Reactor supplier, long msec) {
        double current = supplier.getCurrentPowerFlow().value();
        double wanted = supplier.getWantedFlow().value();

        supplier.setFlow(Power.of(current + (wanted - current) / 1000 * msec));
    }

    private void supplyEnergy(Component s, double fraction, long msec) {
        s.load(Power.of(s.getCurrentPowerFlow().value() * fraction), msec);
    }

    private void consumeEnergy(Component s, double fraction, long msec) {
        s.load(Power.of(s.getCurrentPowerFlow().value() * fraction), msec);
    }

    private double getQuotient(double required, double available) {
        if (available <= EPSILON) {
            return 0;
        }

        if (available >= required) {
            return 1.0;
        }

        return available / required;
    }
}
