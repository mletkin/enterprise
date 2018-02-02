package org.ully.enterprise;

import java.util.Arrays;

import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.Current;
import org.ully.enterprise.units.Power;

/**
 * configuration for a galaxy class starship
 */
public class Starship {

    public Shield shieldBow = new Shield("bow");
    public Shield shieldStern = new Shield("stern");
    public Phaser phaserBank = new Phaser("main");
    public WarpEngine warpLeft = new WarpEngine("left");
    public WarpEngine warpRight = new WarpEngine("right");

    public Reactor pwrMain = new Reactor("main", Power.of(20));
    public Reactor pwrAux = new Reactor("aux", Power.of(20));

    Circuit mainPowerCircuit = new Circuit();
    Current powerThread;

    public Starship() {
        mainPowerCircuit.supplier = Arrays.asList(pwrAux, pwrMain);
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
