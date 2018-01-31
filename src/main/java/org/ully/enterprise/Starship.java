package org.ully.enterprise;

import java.util.Arrays;

import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.Current;
import org.ully.enterprise.units.Energy;

/**
 * configuration for a galaxy class starship
 */
public class Starship {

    public Shield shieldBow = new Shield();
    public Shield shieldStern = new Shield();
    public Phaser phaserBank = new Phaser();

    public Reactor reactor = new Reactor(Energy.of(10));
    Circuit mainPowerCircuit = new Circuit();
    Current powerThread;

    public Starship() {
        mainPowerCircuit.supplier = reactor;
        mainPowerCircuit.consumer = Arrays.asList(shieldBow, shieldStern, phaserBank);
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
