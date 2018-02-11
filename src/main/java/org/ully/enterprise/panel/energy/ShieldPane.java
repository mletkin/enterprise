package org.ully.enterprise.panel.energy;

import static java.util.Optional.ofNullable;

import org.ully.enterprise.Component;
import org.ully.enterprise.Shield;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Energy;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Panel for the energy flow control of a power shield.
 */
public class ShieldPane extends GridPane implements Refreshable {

    private Gauge gauge;
    private Shield shield;
    private Gauge powerGauge;

    public ShieldPane(Shield shield) {
        super();
        this.shield = shield;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
        add(mkPowerGauge(), 1, 0);
        add(mkLoadSwitch(), 0, 1);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .knobType(KnobType.PLAIN) // Type for center knob (STANDARD, PLAIN, METAL, FLAT)
                .knobColor(btnColor()) // Color of center knob
                .interactive(true) // Should center knob be act as button
                .onButtonReleased(buttonEvent -> toggle()) // Handler (triggered when the center knob was released)
                .title(shield.getName()).subTitle("shield").unit(Energy.SYMBOL).maxValue(shield.getMaxLoad().value())
                .build();
        return gauge;
    }

    private Gauge mkPowerGauge() {
        powerGauge = GaugeBuilder.create().skinType(SkinType.LINEAR) //
                .maxValue(shield.getMaxPower().value()).build();
        return powerGauge;
    }

    private Object toggle() {
        shield.toggle();
        gauge.setKnobColor(btnColor());
        return null;
    }

    private Color btnColor() {
        return shield.isOnline() ? Color.LIGHTGREEN : Color.RED;
    }

    private Switch<Component.Direction> mkLoadSwitch() {
        return new Switch<Component.Direction>()//
                .withOn("load", Component.Direction.IN) //
                .withOff("unload", Component.Direction.OUT) //
                .with(this::onToggle)//
                .get();
    }

    void onToggle(Component.Direction value) {
        shield.setDirection(ofNullable(value).orElse(Component.Direction.IN));
        powerGauge.setMaxValue(shield.getMaxPower().value());
    }

    @Override
    public void refresh() {
        gauge.setValue(shield.getLoad().value());
        powerGauge.setValue(shield.getCurrentPowerFlow().value());
    }
}
