package org.ully.enterprise.fleet;

import java.util.stream.Stream;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.units.Power;

/**
 * Configuration for a starship with a short circuit.
 *
 */
public class Pegasus extends Starship {

    public Pegasus() {
        super("NCC-53847 Pegasus");
    }

    private Circuit shieldCircuit = new Circuit("shield") //
            .with(new Shield("stern"), new Shield("aft"), new Reactor("shield", Power.of(20)));

    private Circuit mainPowerCircuit = new Circuit("main") //
            .with(new Phaser("1st"), new Phaser("2nd"), shieldCircuit);

    private Circuit lifePowerCircuit = new Circuit("life")//
            .with(new LifeSupport("main"), new Reactor("life", Power.of(10)), mainPowerCircuit);

    @Override
    public Stream<Circuit> getCircuits() {
        return Stream.of(mainPowerCircuit, lifePowerCircuit).flatMap(Circuit::getAllCircuits);
    }

}
