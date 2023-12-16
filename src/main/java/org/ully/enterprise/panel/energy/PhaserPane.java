package org.ully.enterprise.panel.energy;

import static java.util.Optional.ofNullable;

import org.ully.enterprise.Component;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.panel.GaugeFactory;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;
import org.ully.enterprise.units.Energy;
import org.ully.enterprise.util.Util;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.NeedleType;
import eu.hansolo.medusa.LcdDesign;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Panel for the energy flow control of a phaser bank.
 */
public class PhaserPane extends GridPane implements Refreshable {

    private Phaser phaser;
    private Gauge loadGauge;
    private Gauge powerGauge;

    /**
     * Creates the panel for a phaser bank.
     *
     * @param phaser
     *                   the phaser bank to monitor
     */
    public PhaserPane(Phaser phaser) {
        this.phaser = phaser;
        setAlignment(Pos.CENTER);

        add(loadGauge = mkLoadGauge(), 0, 0);
        add(powerGauge = mkPowerGauge(), 1, 0);
        add(maxSlider(), 0, 1);
        add(mkLoadSwitch(), 0, 2);
        add(Util.mkButton("fire", event -> phaser.fire()), 1, 2);
    }

    private Slider maxSlider() {
        return SliderBuilder.horizontal() //
                .range(0, Phaser.MAX_LOAD.value()) //
                .value(phaser.getMaxLoad().value()) //
                .onChange(this::setThreshold).get();
    }

    private void setThreshold(Double threshold) {
        loadGauge.setThreshold(threshold);
        phaser.setMaxLoad(Energy.of(threshold));
    }

    private Gauge mkLoadGauge() {
        return GaugeFactory.mkLoadGauge(phaser) //
                .knobType(KnobType.PLAIN).knobColor(btnColor()).interactive(true)
                .onButtonReleased(buttonEvent -> toggle()) //
                .needleType(NeedleType.VARIOMETER) //
                .lcdVisible(true) //
                .lcdDesign(LcdDesign.RED) //
                .threshold(phaser.getMaxLoad().value()) //
                .thresholdVisible(true) //
                .build();
    }

    private Gauge mkPowerGauge() {
        return GaugeFactory.mkPowerGauge(phaser).title("").subTitle("").build();
    }

    private void toggle() {
        phaser.toggle();
        loadGauge.setKnobColor(btnColor());
    }

    private Color btnColor() {
        return phaser.isOnline() ? Color.LIGHTGREEN : Color.RED;
    }

    private Switch<Component.Direction> mkLoadSwitch() {
        return new Switch<Component.Direction>()//
                .withOn("load", Component.Direction.IN) //
                .withOff("unload", Component.Direction.OUT) //
                .with(this::onToggle)//
                .get();
    }

    private void onToggle(Component.Direction value) {
        phaser.setDirection(ofNullable(value).orElse(Component.Direction.IN));
        powerGauge.setMaxValue(phaser.getMaxPower().value());
    }

    @Override
    public void refresh() {
        loadGauge.setValue(phaser.getLoad().value());
        powerGauge.setValue(phaser.getCurrentPowerFlow().value());
    }
}
