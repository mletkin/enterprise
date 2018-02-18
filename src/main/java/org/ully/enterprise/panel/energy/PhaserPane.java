package org.ully.enterprise.panel.energy;

import static java.util.Optional.ofNullable;

import org.ully.enterprise.Component;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Energy;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
     *            the phaser bank to monitor
     */
    public PhaserPane(Phaser phaser) {
        this.phaser = phaser;
        setAlignment(Pos.CENTER);

        add(loadGauge = mkLoadGauge(), 0, 0);
        add(powerGauge = mkPowerGauge(), 1, 0);
        add(mkLoadSwitch(), 0, 1);
        add(mkFireBtn(), 1, 1);
    }

    private Gauge mkLoadGauge() {
        return GaugeBuilder.create().skinType(SkinType.GAUGE) //
                .knobType(KnobType.PLAIN).knobColor(btnColor()).interactive(true)
                .onButtonReleased(buttonEvent -> toggle()) //
                .title(phaser.getName()).subTitle(phaser.type()).unit(Energy.SYMBOL).maxValue(phaser.getMaxLoad().value())
                .build();
    }

    private Gauge mkPowerGauge() {
        return GaugeBuilder.create().skinType(SkinType.LINEAR) //
                .maxValue(phaser.getMaxPower().value()).build();
    }

    private Node mkFireBtn() {
        Button btn = new Button();
        btn.setText("fire");
        btn.setOnAction(event -> phaser.fire());
        return btn;
    }

    private Object toggle() {
        phaser.toggle();
        loadGauge.setKnobColor(btnColor());
        return null;
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

    void onToggle(Component.Direction value) {
        phaser.setDirection(ofNullable(value).orElse(Component.Direction.IN));
        powerGauge.setMaxValue(phaser.getMaxPower().value());
    }

    @Override
    public void refresh() {
        loadGauge.setValue(phaser.getLoad().value());
        powerGauge.setValue(phaser.getCurrentPowerFlow().value());
    }
}
