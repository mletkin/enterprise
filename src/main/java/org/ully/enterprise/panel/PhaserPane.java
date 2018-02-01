package org.ully.enterprise.panel;

import org.ully.enterprise.Phaser;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PhaserPane extends GridPane {

    private Gauge gauge;
    private Phaser phaser;

    public PhaserPane(String title, Phaser phaser) {
        super();
        this.phaser = phaser;
        setAlignment(Pos.CENTER);

        add(mkGauge(title, phaser), 0, 0);
        add(mkFireBtn(), 0, 1);
    }

    private Gauge mkGauge(String title, Phaser phaser) {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .title(title).subTitle("phaser").unit("E").maxValue(phaser.getMaxLoad().value()).build();
        return gauge;
    }

    private Node mkFireBtn() {
        Button btn = new Button();
        btn.setText("fire");
        btn.setOnAction(event -> phaser.fire());
        return btn;
    }

    public void refresh() {
        gauge.setValue(phaser.getLoad().value());
    }
}
