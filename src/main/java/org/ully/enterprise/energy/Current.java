package org.ully.enterprise.energy;

/**
 * Power flow emulation class
 */
public class Current extends Thread {

    private static final long DELTA_IN_MSEC = 10;
    private Circuit circuit;

    public Current(Circuit circuit) {
        this.circuit = circuit;
    }

    @Override
    public void run() {
        Cycle cycle = new Cycle(circuit);
        for (;;) {
            cycle.calculate(DELTA_IN_MSEC);
            try {
                sleep(DELTA_IN_MSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
