package org.ully.enterprise.motion;

import java.util.stream.Stream;

import org.ully.enterprise.Engine;
import org.ully.enterprise.ImpulseEngine;
import org.ully.enterprise.MachineAggregate;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.units.Force;
import org.ully.enterprise.units.Power;

/**
 * single engine test ship.
 */
public class TestShip extends Starship {
    Force force = Force.of(5);
    Power power = Power.of(0.025);

    boolean stop = false;

    public TestShip() {
        super("");
    }

    public void cutEngines() {
        stop = true;
    }

    @Override
    public Stream<MachineAggregate> drives() {
        return Stream.of(new MachineAggregate(this, mkEngine(force), mkEngine(force)));
    }

    @Override
    public Stream<Engine> engines() {
        return Stream.of(mkEngine(Force.of(10)));
    }

    private ImpulseEngine mkEngine(Force force) {
        return new ImpulseEngine("") {

            @Override
            public Force getForce(double mass) {
                return stop ? Force.ZERO : force;
            }

            @Override
            public Power getPower() {
                return stop ? Power.ZERO : power;
            }
        };
    }

    @Override
    public Circuit powerSystem() {
        return null;
    }

    @Override
    public double mass() {
        return 1;
    }

    @Override
    public double angularMass() {
        return mass();
    }

}