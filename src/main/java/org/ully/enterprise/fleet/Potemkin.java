package org.ully.enterprise.fleet;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.units.Power;

/**
 * Configuration for a galaxy class starship.
 *
 * <ul>
 * <li>The starship contains power consuming and supplying components.
 * <li>The components are connected through (energy) circuits.
 * <li>The circuits are seperated and not connected.
 * <li>A single poewer emulator emulates he energy flow within all the circuits.
 * </ul>
 */
public class Potemkin extends Starship {

    private Circuit shieldCircuit = new Circuit("shield") //
            .with(new Shield("stern"), new Shield("aft"), new Reactor("life", Power.of(20)));

    private Circuit mainPowerCircuit = new Circuit("main") //
            .with(new Phaser("1st"), new Phaser("2nd"), shieldCircuit);

    private Circuit lifePowerCircuit = new Circuit("life")//
            .with(new LifeSupport("main"), new Reactor("life", Power.of(10)));

    private Circuit bus = new Circuit("energy bus") //
            .with(mainPowerCircuit, lifePowerCircuit);

    public Potemkin() {
        super("NCC-1657 Potemkin");
    }

    @Override
    public Circuit powerSystem() {
        return bus;
    }

    @Override
    public double mass() {
        return 200;
    }

    @Override
    public double angularMass() {
        return 200;
    }

}
