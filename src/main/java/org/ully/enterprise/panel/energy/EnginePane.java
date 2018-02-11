package org.ully.enterprise.panel.energy;

import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Panel for the energy flow control of a warp or impulse engine.
 */
public class EnginePane extends GridPane implements Refreshable {

    private Gauge gauge;
    private WarpEngine engine;

    public EnginePane(WarpEngine engine) {
        super();
        this.engine = engine;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE) //
                .knobType(KnobType.PLAIN)
                .knobColor(btnColor())
                .interactive(true)
                .onButtonReleased(buttonEvent -> toggle())
                .title(engine.getName()).subTitle("warp").unit(Power.SYMBOL).maxValue(engine.getMaxPower().value()).build();
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

    @Override
    public void refresh() {
        gauge.setValue(engine.getCurrentPowerFlow().value());
    }
}
