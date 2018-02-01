package org.ully.enterprise.panel;

import org.ully.enterprise.Shield;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class ShieldPane extends GridPane {

    private Gauge gauge;
    private Shield shield;

    public ShieldPane(String title, Shield shield) {
        super();
        this.shield = shield;
        setAlignment(Pos.CENTER);

        add(mkGauge(title, shield), 0, 0);
    }

    private Gauge mkGauge(String title, Shield shield) {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .title(title).subTitle("shield").unit("E").maxValue(shield.getMaxLoad().value()).build();
        return gauge;
    }

    public void refresh() {
        gauge.setValue(shield.getLoad().value());
    }
}
