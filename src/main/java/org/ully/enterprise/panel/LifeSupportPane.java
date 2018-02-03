package org.ully.enterprise.panel;

import org.ully.enterprise.LifeSupport;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class LifeSupportPane extends GridPane {

    private Gauge gauge;
    private LifeSupport system;

    public LifeSupportPane(LifeSupport system) {
        super();
        this.system = system;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .title(system.getName()).subTitle("life").unit("E").maxValue(system.getMax().value()).build();
        return gauge;
    }


    public void refresh() {
        gauge.setValue(system.getPower().value());
    }
}
