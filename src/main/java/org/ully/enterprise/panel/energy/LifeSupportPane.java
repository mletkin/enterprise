package org.ully.enterprise.panel.energy;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.panel.GaugeFactory;
import org.ully.enterprise.panel.Refreshable;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.SectionBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Panel for the energy flow control of a life support system.
 */
public class LifeSupportPane extends GridPane implements Refreshable {

    private LifeSupport system;
    private Gauge gauge;

    /**
     * Creates the panel for a life support system.
     *
     * @param system
     *            the system to monitor
     */
    public LifeSupportPane(LifeSupport system) {
        this.system = system;
        setAlignment(Pos.CENTER);
        add(gauge = mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        Section section = SectionBuilder.create().color(Color.CRIMSON).start(0).stop(2).build();
        return GaugeFactory.mkPowerGauge(system) //
                .needleColor(Color.DARKBLUE) //
                .areas(section) //
                .areasVisible(true) //
                .build();
    }

    @Override
    public void refresh() {
        gauge.setValue(system.getCurrentPowerFlow().value());
    }
}
