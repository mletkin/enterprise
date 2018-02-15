package org.ully.enterprise.fleet;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Starship;
import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.units.Power;

/**
 * Configuration for the USS Entrprise.
 */
public class Enterprise extends Starship {

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

    public final Circuit mainPowerCircuit = new Circuit("main").with( //
            pwrAux, pwrMain, //
            shieldBow, shieldStern, //
            phaserPrime, phaserSec, //
            warpLeft, warpRight);

    public final Circuit lifePowerCircuit = new Circuit("life").with(pwrLife, life);

    public Enterprise() {
        super("NCC-1701 Enterprise");
    }

    @Override
    public Circuit powerSystem() {
        return new Circuit("power bus").with(mainPowerCircuit, lifePowerCircuit);
    }

    @Override
    public double mass() {
        return 400.0;
    }
}
