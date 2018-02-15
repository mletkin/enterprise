package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.Starship;

public class TestUtil {

    public static Circuit c(Component... cList) {
        return new Circuit("").with(cList);
    }

    abstract static class TestShip extends Starship {
        public TestShip() {
            super("");
        }

        @Override
        public double mass() {
            return 0;
        }
    }

    static Starship ship(Circuit circuit) {
        return new TestShip() {
            @Override
            public Circuit powerSystem() {
                return circuit;
            }
        };
    }
}
