package org.ully.enterprise.panel.energy;

import org.ully.enterprise.Reactor;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.LcdFont;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class ReactorPane extends GridPane implements Refreshable {

    private Gauge gauge;
    private Reactor reactor;

    public ReactorPane(Reactor reactor) {
        super();
        this.reactor = reactor;
        setAlignment(Pos.CENTER);

        add(mkGauge(), 0, 0);
        add(mkSlider(), 1, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.AMP)//
                .title(reactor.getName()).subTitle("pwr").unit(Power.SYMBOL).maxValue(reactor.getMaxPower().value())
                .threshold(reactor.getWantedFlow().value()).thresholdVisible(true) //
                .lcdVisible(true)
                .lcdDesign(LcdDesign.BLACK_YELLOW)
                .lcdFont(LcdFont.DIGITAL)
                .build();
        return gauge;
    }

    private Slider mkSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(reactor.getMaxPower().value());
        slider.setValue(reactor.getCurrentPowerFlow().value());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(2);
        slider.setBlockIncrement(1);
        slider.setOrientation(Orientation.VERTICAL);
        slider.valueProperty().addListener(//
                (ChangeListener<Number>) (observable, oldValue, newValue) -> {
                    reactor.setWantedFlow(Power.of(newValue.doubleValue()));
                });
        return slider;
    }

    @Override
    public void refresh() {
        gauge.setValue(reactor.getCurrentPowerFlow().value());

        if (!reactor.getCurrentPowerFlow().equals(reactor.getWantedFlow())
                && !Power.of(gauge.getThreshold()).equals(reactor.getWantedFlow())) {
            gauge.setThreshold(reactor.getWantedFlow().value());
        }

    }
}