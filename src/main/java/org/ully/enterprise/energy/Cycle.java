package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;

/**
 * What happens within a single loading/discharge cycle for a circuit.
 * <p>
 * Emulates the energy flow within a single time unit.
 * <p>
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
        double supplyQuotient = getQuotientSupplied(required, available);
        double consumeQuotient = getQuotientConsumed(required, available);

        circuit.getConsumer().forEach(s -> this.supplyEnergy(s, supplyQuotient, msec));
        circuit.getSupplier().forEach(s -> this.consumeEnergy(s, consumeQuotient, msec));
        circuit.getComponents().forEach(c -> c.internal(msec));
    }

    private double energySupplied(long msec) {
        return circuit.getSupplier()//
                .map(c -> c.getPotentialEnergyFlow(msec)) //
                .mapToDouble(Energy::value)//
                .sum();
    }

    private double energyRequired(long msec) {
        return circuit.getConsumer()//
                .map(c -> c.getPotentialEnergyFlow(msec)) //
                .mapToDouble(Energy::value)//
                .sum();
    }

    private void supplyEnergy(Component s, double fraction, long msec) {
        s.load(Energy.of(s.getPotentialEnergyFlow(msec).value() * fraction), msec);
    }

    private void consumeEnergy(Component s, double fraction, long msec) {
        supplyEnergy(s, fraction, msec);
    }

    /**
     * calculate the fraction of energy that will actually be supplied.
     *
     * @param required
     * @param available
     * @return
     */
    private double getQuotientSupplied(double required, double available) {
        if (Math.abs(available) <= EPSILON || Math.abs(required) <= EPSILON) {
            return 0;
        }

        if (available >= required) {
            return 1.0;
        }

        return available / required;
    }

    /**
     * calculate the fraction of energy that will actually be consumed.
     *
     * @param required
     * @param available
     * @return
     */
    private double getQuotientConsumed(double required, double available) {
        if (Math.abs(available) <= EPSILON || Math.abs(required) <= EPSILON) {
            return 0;
        }

        if (available >= required) {
            return required / available;
        }

        return 1.0;
    }

}
