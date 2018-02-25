package org.ully.enterprise.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.Starship;
import org.ully.enterprise.motion.TestShip;

public class TestUtil {

    public static Circuit c(Component... cList) {
        return new Circuit("").with(cList);
    }


    static Starship ship(Circuit circuit) {
        return new TestShip() {
            @Override
            public Circuit powerSystem() {
                return circuit;
            }

            @Override
            public double mass() {
                return 0;
            }
        };
    }
}
