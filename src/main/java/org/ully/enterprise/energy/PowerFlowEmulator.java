package org.ully.enterprise.energy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ully.enterprise.units.Power;

/**
 * Power flow emulation.
 */
public class PowerFlowEmulator extends Thread {

    private static final long DELTA_IN_MSEC = 10;
    private Circuit[] circuit;

    /**
     * Create an emulator for a list of circuits.
     *
     * @param circuit
     */
    public PowerFlowEmulator(Circuit... circuit) {
        this.circuit = circuit;
    }

    @Override
    public void run() {
        List<Cycle> cycleList = mkCycles();

        for (;;) {
            calculateSingleCycle(cycleList);
            try {
                sleep(DELTA_IN_MSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void calculateSingleCycle(List<Cycle> cycleList) {
        // reset the gateway power in each circuit
        Stream.of(circuit).forEach(c -> c.setGatewayPower(Power.ZERO));

        // calculate gateway power in eah circuit
        new Cycle(new Circuit(circuit)).calculate(DELTA_IN_MSEC);

        // calculate power flow in each cycle wrapped circuit
        cycleList.forEach(c -> c.calculate(DELTA_IN_MSEC));
    }

    private List<Cycle> mkCycles() {
        return Stream.of(circuit) //
                .filter(Objects::nonNull) //
                .map(Cycle::new) //
                .collect(Collectors.toList());
    }
}
