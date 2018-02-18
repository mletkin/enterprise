package org.ully.enterprise.panel;

import org.ully.enterprise.Component;
import org.ully.enterprise.Engine;
import org.ully.enterprise.Loadable;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.energy.PowerGateway;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.units.PhysicalUnit;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;

/**
 * Centralized configuration of gauges.
 */
public class GaugeFactory {

    public static <T extends Component & Loadable> GaugeBuilder mkLoadGauge(T component) {
        return mkGauge(component, skin(component, Energy.class)) //
                .unit(Energy.SYMBOL) //
                .maxValue(component.getMaxLoad().value());
    }

    public static GaugeBuilder mkPowerGauge(Component component) {
        return mkGauge(component, skin(component, Power.class)) //
                .unit(Power.SYMBOL) //
                .maxValue(component.getMaxPower().value());
    }

    private static GaugeBuilder mkGauge(Component component, SkinType type) {
        return GaugeBuilder.create() //
                .skinType(type) //
                .title(component.getName()) //
                .subTitle(component.type());
    }

    private static SkinType skin(Component component, Class<? extends PhysicalUnit> unit) {
        if (Power.class.equals(unit)) {
            if (Shield.class.equals(component.getClass()) || Phaser.class.equals(component.getClass())) {
                return SkinType.LINEAR;
            }
            if (Reactor.class.equals(component.getClass())) {
                return SkinType.AMP;
            }
            if (Engine.class.isAssignableFrom(component.getClass())) {
                return SkinType.DIGITAL;
            }
            if (PowerGateway.class.equals(component.getClass())) {
                return SkinType.INDICATOR;
            }
        }
        return SkinType.GAUGE;
    }
}
