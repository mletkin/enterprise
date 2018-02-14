package org.ully.enterprise.energy;

/**
 * Perform the power flow emulation.
 * <p>
 * Repeatedly performs the power flow emulation for a set of
 * {@code Circuit}-Objects with a configurable time interval in an endless loop.
 */
public class PowerFlowEmulator extends Thread {

    private static final long DELTA_IN_MSEC = 10;
    private long delta;
    private Cycle cycle;
    private Circuit circuit;

    /**
     * Create an emulator object for a given tme interval.
     *
     * @param delta
     *            time interval in milliseconds
     */
    private PowerFlowEmulator(long delta) {
        this.delta = delta;
        cycle = new Cycle(delta);
    }

    /**
     * Creates an emulator for a given interval.
     *
     * @param delta
     *            time interval in milliseconds
     * @return the new emulator instance
     */
    public static PowerFlowEmulator get(long delta) {
        return new PowerFlowEmulator(delta);
    }

    /**
     * Creates an emulator for the standard interval.
     *
     * @return the new emulator instance
     */
    public static PowerFlowEmulator get() {
        return new PowerFlowEmulator(DELTA_IN_MSEC);
    }

    /**
     * Use the given list of circuits.
     *
     * @param circuit
     *            the circuit for which to emulate the power flow
     * @return the {@code PowerFlowEmulator}-Object
     */
    public PowerFlowEmulator with(Circuit circuit) {
        this.circuit = circuit;
        return this;
    }

    @Override
    public void run() {
        for (;;) {
            calculateSingleCycle();
            try {
                sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Perform the power flow emulation for a single cycle.
     */
    void calculateSingleCycle() {

        // reset the gateway power in each circuit (and sub-circuits)
        circuit.resetGateway();

        // calculate gateway power for all circuits (and sub-circuits)
        circuit.calculate();

        // calculate gateway power for all circuits
        cycle.calculate(circuit);
    }

}
