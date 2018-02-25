package org.ully.enterprise.panel.energy;

import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.panel.GaugeFactory;
import org.ully.enterprise.panel.Refreshable;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Panel for the energy flow control of a warp or impulse engine.
 */
public class EnginePane extends GridPane implements Refreshable {

    private Gauge gauge;
    private WarpEngine engine;

    /**
     * Creates an engine panel.
     *
     * @param engine
     *            the engine to monitor
     */
    public EnginePane(WarpEngine engine) {
        this.engine = engine;
        setAlignment(Pos.CENTER);

        add(gauge = mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        return GaugeFactory.mkPowerGauge(engine) //
                .knobType(KnobType.PLAIN).knobColor(btnColor()).interactive(true)
                .onButtonReleased(buttonEvent -> toggle())//
                .build();
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
