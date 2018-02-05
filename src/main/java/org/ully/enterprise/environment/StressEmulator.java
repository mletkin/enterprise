package org.ully.enterprise.environment;

/**
 * Stress applying Thread.
 */
public class StressEmulator extends Thread {

    private static final long DELTA_IN_MSEC = 10;
    private Stress stress;

    public static void emulate(Stress stress) {
        (new StressEmulator(stress)).start();
    }

    public StressEmulator(Stress stress) {
        this.stress = stress;
    }

    @Override
    public void run() {
        while (stress.isActive()) {
            stress.apply(DELTA_IN_MSEC);
            try {
                sleep(DELTA_IN_MSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
