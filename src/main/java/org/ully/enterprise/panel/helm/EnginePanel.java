package org.ully.enterprise.panel.helm;

import org.ully.enterprise.WarpEngine;
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

    private WarpEngine warpLeft;
    private WarpEngine warpRight;

    private Gauge leftGauge;
    private Gauge rightGauge;

    private Slider balance;
    private Slider warp;

    /**
     * Creates the panel.
     *
     * @param left
     *            left engine
     * @param right
     *            right engine
     */
    public EnginePanel(WarpEngine left, WarpEngine right) {
        super();
        this.warpLeft = left;
        this.warpRight = right;
        setAlignment(Pos.CENTER);

        add(leftGauge = mkGauge(this.warpLeft), 0, 0);
        add(rightGauge = mkGauge(this.warpRight), 2, 0);
        add(warp = mkWarpSlider(), 0, 1, 3, 1);
        add(balance = mkBalanceSlider(), 0, 2, 3, 1);
        add(mkCenterBtn(), 2, 4);
    }

    private Gauge mkGauge(WarpEngine engine) {
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
                .range(-50, 50) //
                .onChange(value -> setWarpFactor(warp.getValue(), value)) //
                .get();
    }

    private void setWarpFactor(double speed, double balance) {
        warpLeft.setWantedWarp(speed * (1 - balance / 100));
        warpRight.setWantedWarp(speed * (1 + balance / 100));
    }

    @Override
    public void refresh() {
        leftGauge.setValue(warpLeft.getCurrentWarp());
        leftGauge.setThreshold(warpLeft.getWantedWarp());
        rightGauge.setValue(warpRight.getCurrentWarp());
        rightGauge.setThreshold(warpRight.getWantedWarp());
    }

}
