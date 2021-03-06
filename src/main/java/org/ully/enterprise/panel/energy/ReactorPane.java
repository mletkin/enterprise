package org.ully.enterprise.panel.energy;

import org.ully.enterprise.Reactor;
import org.ully.enterprise.panel.GaugeFactory;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.LcdFont;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel for the energy flow control of a energy supplying reactor.
 */
public class ReactorPane extends GridPane implements Refreshable {

    private Reactor reactor;
    private Gauge gauge;

    /**
     * Creates a reactor panel.
     *
     * @param reactor
     *            the reactor to monitor
     */
    public ReactorPane(Reactor reactor) {
        this.reactor = reactor;
        setAlignment(Pos.CENTER);

        add(gauge = mkGauge(), 0, 0);
        add(mkSlider(), 1, 0);
    }

    private Gauge mkGauge() {
        return GaugeFactory.mkPowerGauge(reactor) //
                .threshold(reactor.getWantedFlow().value()).thresholdVisible(true) //
                .lcdVisible(true).lcdDesign(LcdDesign.BLACK_YELLOW).lcdFont(LcdFont.DIGITAL).build();
    }

    private Slider mkSlider() {
        return SliderBuilder.vertical() //
                .range(0, reactor.getMaxPower().value()) //
                .value(reactor.getCurrentPowerFlow().value()) //
                .withMajorTickUnit(1) //
                .onChange(value -> reactor.setWantedFlow(Power.of(value))).get();
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
