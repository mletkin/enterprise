package org.ully.enterprise.motion;

import java.util.stream.Stream;

import org.ully.enterprise.Engine;
import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.units.Force;

/**
 * single engine test ship.
 */
public class TestShip extends Starship {
    Force force = Force.of(10);

    public TestShip() {
        super("");
    }

    @Override
    public Stream<Engine> engines() {
        return Stream.of(mass -> force);
    }

    @Override
    public Circuit powerSystem() {
        return null;
    }

    @Override
    public double mass() {
        return 1;
    }

}