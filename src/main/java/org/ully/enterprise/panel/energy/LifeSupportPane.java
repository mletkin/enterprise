package org.ully.enterprise.panel.energy;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.SectionBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Panel for the energy flow control of a life support system.
 */
public class LifeSupportPane extends GridPane implements Refreshable {

    private Gauge gauge;
    private LifeSupport system;

    public LifeSupportPane(LifeSupport system) {
        super();
        this.system = system;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {

        Section section = SectionBuilder.create().color(Color.CRIMSON).start(0).stop(2).build();

        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .title(system.getName()).subTitle("life").unit(Power.SYMBOL).maxValue(system.getMaxPower().value()) //
                .needleColor(Color.DARKBLUE)//
                .areas(section) //
                .areasVisible(true) //
                .build();
        return gauge;
    }

    @Override
    public void refresh() {
        gauge.setValue(system.getCurrentPowerFlow().value());
    }
}
