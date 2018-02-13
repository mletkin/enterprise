package org.ully.enterprise.energy;

import org.ully.enterprise.Component;

public class TestUtil {

    public static Circuit c(Component... cList) {
        return new Circuit("").with(cList);
    }

}
