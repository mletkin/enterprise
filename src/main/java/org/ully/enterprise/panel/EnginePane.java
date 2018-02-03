package org.ully.enterprise.panel;

import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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
                .knobType(KnobType.PLAIN)                                                     // Type for center knob (STANDARD, PLAIN, METAL, FLAT)
                .knobColor(btnColor())                                                      // Color of center knob
                .interactive(true)                                                              // Should center knob be act as button
                .onButtonReleased(buttonEvent -> toggle())            // Handler (triggered when the center knob was released)
                .title(engine.getName()).subTitle("warp").unit(Power.SYMBOL).maxValue(engine.getMax().value()).build();
        return gauge;
    }

    private Object toggle() {
        engine.toggle();
        gauge.setKnobColor(btnColor());
        return null;
    }

    private Color btnColor() {
        return engine.isOnline() ? Color.LIGHTGREEN : Color.RED;
    }

    public void refresh() {
        gauge.setValue(engine.getPower().value());
    }
}
