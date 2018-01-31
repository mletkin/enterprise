package org.ully.enterprise.energy;

import org.ully.enterprise.Loadable;
import org.ully.enterprise.units.Energy;

/**
 * What happens within a single loading/discharge cycle.
 * <p>
 * emulate the energy folw in a single time unit.
 */
public class Cycle {

    private static final double EPSILON = 0.0000001;
    private Circuit circuit;

    public Cycle(Circuit circuit) {
        this.circuit = circuit;
    }

    /**
     * Supply energy to consumer.
     */
    public void calculate(long msec) {
        double available = circuit.supplier.getFlow(msec).value();
        double required = energyRequired(msec);
        double quotient = getQuotient(required, available);

        if (quotient > EPSILON) {
            circuit.consumer.forEach(s -> this.supplyEnergy(s, quotient, msec));
        }
    }

    private void supplyEnergy(Loadable s, double fraction, long msec) {
        s.load(Energy.of(s.getLoadingPower(msec).value() * fraction));
    }

    private double energyRequired(long msec) {
        return circuit.consumer.stream().map(c -> c.getLoadingPower(msec)).mapToDouble(Energy::value).sum();
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
