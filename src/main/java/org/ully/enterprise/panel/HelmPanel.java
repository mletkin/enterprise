package org.ully.enterprise.panel;

import org.ully.enterprise.Starship;
import org.ully.enterprise.WarpEngine;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel for Starship helm.
 */
public class HelmPanel extends GridPane {

    private Starship ship;
    private Gauge leftGauge;
    private Gauge rightGauge;
    private Slider balance;
    private Slider warp;

    /**
     * create the helm panel.
     *
     * @param starship
     */
    public HelmPanel(Starship ship) {
        super();
        this.ship = ship;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        add(leftGauge = mkGauge(ship.warpLeft),   0, 0, 1, 1);
        add(rightGauge = mkGauge(ship.warpRight), 1, 0, 1, 1);
        add(warp = mkWarpSlider(),                0, 1, 2, 1);
        add(balance = mkBalanceSlider(),          0, 2, 2, 1);
        add(mkCenterBtn(),                        0, 3, 2, 1);
    }

    private Gauge mkGauge(WarpEngine engine) {
        return GaugeBuilder.create().skinType(SkinType.HORIZONTAL) //
                .thresholdVisible(true)//
                .title(engine.getName()).subTitle("warp").unit("W").maxValue(12).build();
    }

    private Slider mkWarpSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(12);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(0.5);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.valueProperty().addListener(//
                (ChangeListener<Number>) (observable, oldValue, newValue) -> {
                    setWarpFactor(newValue.doubleValue(), this.balance.getValue());
                });
        return slider;
    }

    private Slider mkBalanceSlider() {
        Slider slider = new Slider();
        slider.setMin(-50);
        slider.setMax(50);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setBlockIncrement(5);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.valueProperty().addListener(//
                (ChangeListener<Number>) (observable, oldValue, newValue) -> {
                    setWarpFactor(warp.getValue(), newValue.doubleValue());
                });
        return slider;
    }


    private void setWarpFactor(double speed, double balance) {
        ship.warpLeft.setWantedWarp(speed * (1 - balance / 100));
        ship.warpRight.setWantedWarp(speed * (1 + balance / 100));
    }

    private Button mkCenterBtn() {
        Button btn = new Button();
        btn.setText("^");
        btn.setOnAction(e -> balance.setValue(0));
        return btn;
    }

    /**
     * Refresh all panels on the grid.
     */
    public void refresh() {
        leftGauge.setValue(ship.warpLeft.getCurrentWarp());
        leftGauge.setThreshold(ship.warpLeft.getWantedWarp());

        rightGauge.setValue(ship.warpRight.getCurrentWarp());
        rightGauge.setThreshold(ship.warpRight.getWantedWarp());
    }

}
