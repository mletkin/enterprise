package org.ully.enterprise.energy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Power flow emulation class
 */
public class Current extends Thread {

    private static final long DELTA_IN_MSEC = 10;
    private Circuit[] circuit;

    public Current(Circuit... circuit) {
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
