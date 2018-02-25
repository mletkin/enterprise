package org.ully.enterprise.panel.helm;

import org.ully.enterprise.Component;
import org.ully.enterprise.MachineAggregate;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel to control two combined engines.
 */
public class EnginePanel extends GridPane implements Refreshable {

    private MachineAggregate aggregate;

    private Gauge leftGauge;
    private Gauge rightGauge;

    private Slider balance;
    private Slider warp;

    /**
     * Creates a panel for an engine group.
     *
     * @param aggregate
     *            the engine group to control
     */
    public EnginePanel(MachineAggregate aggregate) {

        this.aggregate = aggregate;

        setAlignment(Pos.CENTER);

        add(leftGauge = mkGauge(aggregate.left()), 0, 0);
        add(rightGauge = mkGauge(aggregate.right()), 2, 0);
        add(warp = mkWarpSlider(), 0, 1, 3, 1);
        add(balance = mkBalanceSlider(), 0, 2, 3, 1);
        add(mkCenterBtn(), 2, 4);
    }

    private Gauge mkGauge(Component engine) {
        return GaugeBuilder.create().skinType(SkinType.HORIZONTAL) //
                .thresholdVisible(true)//
                .title(engine.getName()).subTitle("warp").unit("W").maxValue(12).build();
    }

    private Button mkCenterBtn() {
        Button btn = new Button();
        btn.setText("^");
        btn.setOnAction(e -> balance.setValue(0));
        return btn;
    }

    private Slider mkWarpSlider() {
        return SliderBuilder.horizontal() //
                .range(0, 12) //
                .withMajorTickUnit(1) //
                .onChange(value -> setWarpFactor(value, this.balance.getValue())) //
                .get();
    }

    private Slider mkBalanceSlider() {
        return SliderBuilder.horizontal() //
                .range(-100, 100) //
                .onChange(value -> setWarpFactor(warp.getValue(), value)) //
                .get();
    }

    private void setWarpFactor(double speed, double balance) {
        aggregate.left().setWantedWarp(speed * (1 - balance / 100));
        aggregate.right().setWantedWarp(speed * (1 + balance / 100));
    }

    @Override
    public void refresh() {
        leftGauge.setValue(aggregate.left().getCurrentWarp());
        leftGauge.setThreshold(aggregate.left().getWantedWarp());
        rightGauge.setValue(aggregate.right().getCurrentWarp());
        rightGauge.setThreshold(aggregate.right().getWantedWarp());
    }

}
