package org.ully.enterprise.panel.energy;

import org.ully.enterprise.Component;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Energy;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.KnobType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PhaserPane extends GridPane implements Refreshable {

    private Phaser phaser;
    private Gauge loadGauge;
    private Gauge powerGauge;

    /**
     * Creates a monitoring panel for a phaser bank.
     *
     * @param phaser
     *            the phaser bank to monitor
     */
    public PhaserPane(Phaser phaser) {
        super();
        this.phaser = phaser;
        setAlignment(Pos.CENTER);

        add(mkLoadGauge(), 0, 0);
        add(mkPowerGauge(), 1, 0);
        add(unloadGroup(), 0, 1);
        add(mkFireBtn(), 1, 1);
    }

    private Gauge mkLoadGauge() {
        loadGauge = GaugeBuilder.create().skinType(SkinType.GAUGE) //
                .knobType(KnobType.PLAIN).knobColor(btnColor()).interactive(true)
                .onButtonReleased(buttonEvent -> toggle()) //
                .title(phaser.getName()).subTitle("phaser").unit(Energy.SYMBOL).maxValue(phaser.getMaxLoad().value())
                .build();
        return loadGauge;
    }

    private Gauge mkPowerGauge() {
        powerGauge = GaugeBuilder.create().skinType(SkinType.LINEAR) //
                .title("pwr").unit(Energy.SYMBOL).maxValue(phaser.getMaxPower().value()).build();
        return powerGauge;
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

    private GridPane unloadGroup() {
        GridPane pane = new GridPane();
        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ov, toggle, new_toggle) -> {
            if (new_toggle == null) {
                phaser.setDirection(Component.Direction.IN);
            } else {
                phaser.setDirection((Component.Direction) group.getSelectedToggle().getUserData());
            }
            powerGauge.setMaxValue(phaser.getMaxPower().value());
        });

        ToggleButton btnLoad = new ToggleButton();
        btnLoad.setText("load");
        btnLoad.setToggleGroup(group);
        btnLoad.setUserData(Component.Direction.IN);
        btnLoad.setSelected(true);
        pane.add(btnLoad, 0, 0);

        ToggleButton btnUnload = new ToggleButton();
        btnUnload.setText("unload");
        btnUnload.setToggleGroup(group);
        btnUnload.setUserData(Component.Direction.OUT);
        btnUnload.setSelected(false);
        pane.add(btnUnload, 1, 0);

        return pane;
    }

    @Override
    public void refresh() {
        loadGauge.setValue(phaser.getLoad().value());
        powerGauge.setValue(phaser.getCurrentPowerFlow().value());
    }
}
