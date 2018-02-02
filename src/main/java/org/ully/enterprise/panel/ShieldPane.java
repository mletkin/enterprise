package org.ully.enterprise.panel;

import org.ully.enterprise.Shield;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ShieldPane extends GridPane {

    private Gauge gauge;
    private Shield shield;

    public ShieldPane(Shield shield) {
        super();
        this.shield = shield;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .knobType(KnobType.PLAIN)                                                     // Type for center knob (STANDARD, PLAIN, METAL, FLAT)
                .knobColor(btnColor())                                                      // Color of center knob
                .interactive(true)                                                              // Should center knob be act as button
                .onButtonReleased(buttonEvent -> toggle())            // Handler (triggered when the center knob was released)
                .title(shield.getName()).subTitle("shield").unit("E").maxValue(shield.getMaxLoad().value()).build();
        return gauge;
    }

    private Object toggle() {
        shield.toggle();
        gauge.setKnobColor(btnColor());
        return null;
    }

    private Color btnColor() {
        return shield.isOnline() ? Color.LIGHTGREEN : Color.RED;
    }

    public void refresh() {
        gauge.setValue(shield.getLoad().value());
    }
}
