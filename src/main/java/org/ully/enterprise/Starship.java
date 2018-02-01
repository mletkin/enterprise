package org.ully.enterprise;

import java.util.Arrays;

import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.Current;
import org.ully.enterprise.units.Power;

/**
 * configuration for a galaxy class starship
 */
public class Starship {

    public Shield shieldBow = new Shield();
    public Shield shieldStern = new Shield();
    public Phaser phaserBank = new Phaser();
    public WarpEngine warpLeft = new WarpEngine();
    public WarpEngine warpRight = new WarpEngine();
    public Reactor pwrMain = new Reactor(Power.of(20));
    public Reactor pwrAux = new Reactor(Power.of(20));

    Circuit mainPowerCircuit = new Circuit();
    Current powerThread;

    public Starship() {
        mainPowerCircuit.supplier = pwrMain;
        mainPowerCircuit.consumer = Arrays.asList(shieldBow, shieldStern, phaserBank,//
                warpLeft, warpRight);
    }

    public void powerUp() {
        if (powerThread == null) {
            powerThread = new Current(mainPowerCircuit);
        }
        powerThread.start();
    }

    public void powerDown() {
        if (powerThread != null) {
            powerThread.stop();
        }
    }
}
