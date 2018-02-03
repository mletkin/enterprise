package org.ully.enterprise;

import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.PowerFlowEmulator;
import org.ully.enterprise.units.Power;

/**
 * Configuration for a galaxy class starship
 */
public class Starship {

    public final Shield shieldBow = new Shield("bow");
    public final Shield shieldStern = new Shield("stern");

    public final Phaser phaserPrime = new Phaser("1st");
    public final Phaser phaserSec = new Phaser("2nd");

    public final WarpEngine warpLeft = new WarpEngine("left");
    public final WarpEngine warpRight = new WarpEngine("right");

    public final LifeSupport life = new LifeSupport("main");

    public final Reactor pwrMain = new Reactor("main", Power.of(20));
    public final Reactor pwrAux = new Reactor("aux", Power.of(20));
    public final Reactor pwrLife = new Reactor("life", Power.of(10));

    private Circuit mainPowerCircuit = new Circuit(//
            pwrAux, pwrMain, //
            shieldBow, shieldStern, //
            phaserPrime, phaserSec, //
            warpLeft, warpRight);

    private Circuit lifePowerCircuit = new Circuit(pwrLife, life);

    private PowerFlowEmulator PowerFlowEmulator;

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
