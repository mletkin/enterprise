package org.ully.enterprise.panel;

import org.ully.enterprise.WarpEngine;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class EnginePane extends GridPane {

    private Gauge gauge;
    private WarpEngine engine;

    public EnginePane(WarpEngine engine) {
        super();
        this.engine = engine;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .title(engine.getName()).subTitle("warp").unit("E").maxValue(engine.getMax().value()).build();
        return gauge;
    }

    public void refresh() {
        gauge.setValue(engine.getPower().value());
    }
}
