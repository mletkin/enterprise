package org.ully.enterprise.panel;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.LedType;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class LifeSupportPane extends GridPane implements Refreshable {

    private Gauge gauge;
    private LifeSupport system;

    public LifeSupportPane(LifeSupport system) {
        super();
        this.system = system;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.GAUGE)//
                .ledVisible(true) //
                .ledType(LedType.STANDARD) //
                .ledColor(Color.color(1, 0, 0)) //
                .ledBlinking(true) //
                .ledOn(false) //
                .title(system.getName()).subTitle("life").unit(Power.SYMBOL).maxValue(system.getMaxPower().value())
                .build();
        return gauge;
    }

    @Override
    public void refresh() {
        gauge.setValue(system.getPower().value());
        if (gauge.isLedOn() != system.getCurrentPowerFlow().value() <5) {
            gauge.setLedOn(system.getCurrentPowerFlow().value() <5);
        }
    }
}
