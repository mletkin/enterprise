package org.ully.enterprise.panel;

import org.ully.enterprise.Reactor;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class ReactorPane extends GridPane {

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
                .title(reactor.getName()).subTitle("pwr").unit(Power.SYMBOL).maxValue(reactor.maxFlow().value()).build();
        return gauge;
    }

    private Slider mkSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(reactor.maxFlow().value());
        slider.setValue(reactor.getFlow().value());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setOrientation(Orientation.VERTICAL);
        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            reactor.setWantedFlow(Power.of(newValue.doubleValue()));
        };
        slider.valueProperty().addListener(listener);
        return slider;
    }

    public void refresh() {
        gauge.setValue(reactor.getFlow().value());
        System.out.println("refresh " + reactor.getName());
    }
}
