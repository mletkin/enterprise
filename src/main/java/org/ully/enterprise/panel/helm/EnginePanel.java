package org.ully.enterprise.panel.helm;

import org.ully.enterprise.Component;
import org.ully.enterprise.MachineAggregate;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;
import org.ully.enterprise.units.Power;
import org.ully.enterprise.util.Util;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel to control two combined engines.
 */
public class EnginePanel extends GridPane implements Refreshable {

    private MachineAggregate<?> aggregate;

    private Gauge leftGauge;
    private Gauge rightGauge;

    private Slider balance;
    private Slider power;

    /**
     * Creates a panel for an engine group.
     *
     * @param aggregate
     *            the engine group to control
     */
    public EnginePanel(MachineAggregate<?> aggregate) {

        this.aggregate = aggregate;

        setAlignment(Pos.CENTER);

        add(leftGauge = mkGauge(aggregate.left()), 0, 0);
        add(rightGauge = mkGauge(aggregate.right()), 2, 0);
        add(power = mkPowerSlider(), 0, 1, 3, 1);
        add(balance = mkBalanceSlider(), 0, 2, 3, 1);
        add(Util.mkButton("^", e -> balance.setValue(0)), 2, 4);
    }

    private Gauge mkGauge(Component engine) {
        return GaugeBuilder.create().skinType(SkinType.HORIZONTAL) //
                .thresholdVisible(true)//
                .title(engine.getName()).subTitle("power").unit(Power.SYMBOL).maxValue(12).build();
    }

    private Slider mkPowerSlider() {
        return SliderBuilder.horizontal() //
                .range(0, 12) //
                .withMajorTickUnit(1) //
                .onChange(value -> setEnginePower(value, this.balance.getValue())) //
                .get();
    }

    private Slider mkBalanceSlider() {
        return SliderBuilder.horizontal() //
                .range(-100, 100) //
                .onChange(value -> setEnginePower(power.getValue(), value)) //
                .get();
    }

    private void setEnginePower(double speed, double balance) {
        aggregate.left().setWantedPower(Power.of(speed * (1 - balance / 100)));
        aggregate.right().setWantedPower(Power.of(speed * (1 + balance / 100)));
    }

    @Override
    public void refresh() {
        leftGauge.setValue(aggregate.left().getPower().value());
        leftGauge.setThreshold(aggregate.left().getWantedPower().value());
        rightGauge.setValue(aggregate.right().getPower().value());
        rightGauge.setThreshold(aggregate.right().getWantedPower().value());
    }

}
