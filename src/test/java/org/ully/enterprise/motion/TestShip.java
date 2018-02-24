package org.ully.enterprise.motion;

import java.util.stream.Stream;

import org.ully.enterprise.Engine;
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
    Power power = Power.of(5);

    public TestShip() {
        super("");
    }

    @Override
    public Stream<MachineAggregate> drives() {
        return Stream.of(new MachineAggregate(this, mkEngine(), mkEngine()));
    }

    private Engine mkEngine() {
        return new Engine() {

            @Override
            public Force getForce(double mass) {
                return force;
            }

            @Override
            public Power getPower() {
                return power;
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