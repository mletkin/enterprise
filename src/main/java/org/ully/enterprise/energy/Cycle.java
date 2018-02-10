package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.units.Energy;

/**
 * Calculates the energy flow of single loading/discharge cycle for a circuit.
 * <p>
 * Emulates the energy flow within a given time unit.
 * <p>
 * FIXME: components may switch flow direction while calculating
 */
public class Cycle {

    private static final double EPSILON = 0.00000000001;
    private long msec;

    /**
     * Create a cycle calculator for the given time interval.
     *
     * @param msec
     *            interval length in milliseconds
     */
    public Cycle(long msec) {
        this.msec = msec;
    }

    /**
     * calculate and perform the energy transfer.
     */
    public void calculate(Circuit circuit) {

        double available = energySupplied(circuit);
        double required = energyRequired(circuit);
        double supplyQuotient = getQuotientSupplied(required, available);
        double consumeQuotient = getQuotientConsumed(required, available);

        circuit.getComponents().forEach(c -> c.internal(msec));
        circuit.getConsumer().forEach(s -> this.supplyEnergy(s, supplyQuotient, msec));
        circuit.getSupplier().forEach(s -> this.consumeEnergy(s, consumeQuotient, msec));
    }

    private double energySupplied(Circuit circuit) {
        return circuit.getSupplier()//
                .map(c -> c.getPotentialEnergyFlow(msec)) //
                .mapToDouble(Energy::value)//
                .sum();
    }

    private double energyRequired(Circuit circuit) {
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
