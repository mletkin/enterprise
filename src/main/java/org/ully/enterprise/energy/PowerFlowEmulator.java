package org.ully.enterprise.energy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Cycle> cycle = mkCycles();
        for (;;) {
            cycle.forEach(c -> c.calculate(DELTA_IN_MSEC));
            try {
                sleep(DELTA_IN_MSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private List<Cycle> mkCycles() {
        return Stream.of(circuit) //
                .filter(Objects::nonNull) //
                .map(Cycle::new) //
                .collect(Collectors.toList());
    }
}
