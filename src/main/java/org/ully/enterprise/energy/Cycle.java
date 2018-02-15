package org.ully.enterprise.energy;

import static org.ully.enterprise.util.Util.isZero;

import org.ully.enterprise.Component;
import org.ully.enterprise.Starship;
import org.ully.enterprise.units.Energy;

/**
 * Calculates the energy flow of single loading/discharge cycle for a circuit.
 * <p>
 * Emulates the energy flow within a given time unit.
 * <p>
 * FIXME: components may switch flow direction while calculating
 */
public class Cycle {

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
     * Calculate power flow emulation for a single cycle.
     */
    void calculateSingleCycle(Starship ship) {

        Circuit circuit = ship.powerSystem();

        // reset the gateway power in each circuit (and sub-circuits)
        circuit.resetGateway();

        // calculate gateway power for all circuits (and sub-circuits)
        circuit.calculate();

        // calculate gateway power for all circuits
        calculate(circuit);
    }

    /**
     * Recursively calculates and performs the energy transfer.
     *
     * @param circuit
     *            the circuit to be calculated
     */
    private void calculate(Circuit circuit) {
        double available = energySupplied(circuit);
        double required = energyRequired(circuit);
        double supplyQuotient = getQuotientSupplied(required, available);
        double consumeQuotient = getQuotientConsumed(required, available);

        circuit.getComponents().forEach(c -> c.internal(msec));
        circuit.getConsumer().forEach(s -> distributeEnergy(s, supplyQuotient, msec));
        circuit.getSupplier().forEach(s -> distributeEnergy(s, consumeQuotient, msec));

        circuit.getSubCircuits().forEach(this::calculate);
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

    private void distributeEnergy(Component s, double fraction, long msec) {
        s.load(Energy.of(s.getPotentialEnergyFlow(msec).value() * fraction), msec);
    }

    /**
     * calculate the fraction of energy that will actually be supplied.
     *
     * @param required
     * @param available
     * @return
     */
    private double getQuotientSupplied(double required, double available) {
        if (isZero(available) || isZero(required)) {
            return 0.0;
        }
        return (available >= required) ? 1.0 : available / required;
    }

    /**
     * calculate the fraction of energy that will actually be consumed.
     *
     * @param required
     * @param available
     * @return
     */
    private double getQuotientConsumed(double required, double available) {
        if (isZero(available) || isZero(required)) {
            return 0;
        }
        return (available >= required) ? required / available : 1.0;
    }

}
