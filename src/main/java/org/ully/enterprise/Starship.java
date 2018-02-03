package org.ully.enterprise;

import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.PowerFlowEmulator;
import org.ully.enterprise.units.Power;

/**
 * Configuration for a galaxy class starship
 */
public class Starship {

    public Shield shieldBow = new Shield("bow");
    public Shield shieldStern = new Shield("stern");
    public Phaser phaserBank = new Phaser("main");
    public WarpEngine warpLeft = new WarpEngine("left");
    public WarpEngine warpRight = new WarpEngine("right");

    public LifeSupport life = new LifeSupport("main");

    public Reactor pwrMain = new Reactor("main", Power.of(20));
    public Reactor pwrAux = new Reactor("aux", Power.of(20));
    public Reactor pwrLife = new Reactor("life", Power.of(10));

    Circuit mainPowerCircuit = new Circuit(pwrAux, pwrMain, shieldBow, shieldStern, phaserBank, warpLeft, warpRight);
    Circuit lifePowerCircuit = new Circuit(pwrLife, life);

    PowerFlowEmulator PowerFlowEmulator;

    public void powerUp() {
        if (PowerFlowEmulator == null) {
            PowerFlowEmulator = new PowerFlowEmulator(mainPowerCircuit, lifePowerCircuit);
        }
        PowerFlowEmulator.start();
    }

    public void powerDown() {
        if (PowerFlowEmulator != null) {
            PowerFlowEmulator.stop();
        }
    }
}
