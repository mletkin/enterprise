package org.ully.enterprise.panel;

import org.ully.enterprise.Component;
import org.ully.enterprise.Phaser;

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

public class PhaserPane extends GridPane {

    private Gauge gauge;
    private Phaser phaser;

    public PhaserPane(Phaser phaser) {
        super();
        this.phaser = phaser;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
        add(unloadGroup(), 0, 1);
        add(mkFireBtn(), 1, 1);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .knobType(KnobType.PLAIN) // Type for center knob (STANDARD, PLAIN, METAL, FLAT)
                .knobColor(btnColor()) // Color of center knob
                .interactive(true) // Should center knob be act as button
                .onButtonReleased(buttonEvent -> toggle()) // Handler (triggered when the center knob was released)
                .title(phaser.getName()).subTitle("phaser").unit("E").maxValue(phaser.getMaxLoad().value()).build();
        return gauge;
    }

    private Node mkFireBtn() {
        Button btn = new Button();
        btn.setText("fire");
        btn.setOnAction(event -> phaser.fire());
        return btn;
    }

    private Object toggle() {
        phaser.toggle();
        gauge.setKnobColor(btnColor());
        return null;
    }

    private Color btnColor() {
        return phaser.isOnline() ? Color.LIGHTGREEN : Color.RED;
    }

    private GridPane unloadGroup() {
        GridPane pane = new GridPane();
        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ov, toggle, new_toggle) -> {
            if (new_toggle == null)
                phaser.setDirection(Component.Direction.IN);
            else
                phaser.setDirection((Component.Direction) group.getSelectedToggle().getUserData());
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

    public void refresh() {
        gauge.setValue(phaser.getLoad().value());
    }
}
