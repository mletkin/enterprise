package org.ully.enterprise.fleet;

import java.util.stream.Stream;

import org.ully.enterprise.Engine;
import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.MachineAggregate;
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

    private static double WIDTH = 20.6;
    private static double BREADTH = 20;
    private static double HEIGHT = 50;

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

    private final Circuit bus = new Circuit("power bus").with(mainPowerCircuit, lifePowerCircuit);

    public final MachineAggregate drive = new MachineAggregate(this, warpLeft, warpRight);

    public Enterprise() {
        super("NCC-1701 Enterprise");
    }

    @Override
    public Circuit powerSystem() {
        return bus;
    }

    @Override
    public Stream<Engine> engines() {
        return Stream.of(warpLeft, warpRight);
    }

    @Override
    public Stream<MachineAggregate> drives() {
        return Stream.of(drive);
    }

    @Override
    public double mass() {
        return 400.0;
    }

    /**
     * Angular mass of a flying brick, rotating around the main axis.
     *
     * @see http://www.maschinenbau-wissen.de/skript3/mechanik/kinetik/295-haupttraegheitsmoment
     */
    @Override
    public double angularMass() {
        return 400.0; // 1.0 / 12.0 * mass() * (Math.pow(WIDTH, 2) + Math.pow(BREADTH, 2));
    }

}
