package org.ully.enterprise.panel;

import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Starship;
import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EnerygPanel extends Application {

    private static final SkinType SKIN = SkinType.GAUGE;

    private Starship ship = new Starship();
    private Gauge frontShield = gauge("bow", ship.shieldBow);
    private Gauge rearShield = gauge("stern", ship.shieldStern);

    private Gauge phaserBank = gauge("main", ship.phaserBank);

    private Gauge warpLeft = gauge("left", ship.warpLeft);
    private Gauge warpRight = gauge("right", ship.warpRight);

    private Gauge reactor = gauge("pwrMain", ship.pwrMain);
    private Slider reactorSlider = mkReactorSlider();

//    private Gauge sparkler = sparkGauge("pwrMain", ship.pwrMain);

    private Thread refreshThread = mkRefreshThread();

    public static void main(String[] args) {
        launch(args);
    }

    private Gauge sparkGauge(String string, Reactor reactor) {
        return GaugeBuilder.create().skinType(SkinType.TILE_SPARK_LINE)//
                .title(string).subTitle("pwr").unit("P").maxValue(reactor.maxFlow().value()).build();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Energy panel");

        GridPane grid = mkGrid();
        grid.add(frontShield, 0, 0);
        grid.add(rearShield, 1, 0);
        grid.add(phaserBank, 0, 1);
        grid.add(mkFireBtn(ship.phaserBank), 1, 1);
        grid.add(reactor, 3, 0);
        grid.add(reactorSlider, 4, 0);
//        grid.add(sparkler, 5, 0);
        grid.add(warpLeft, 0, 3);
        grid.add(warpRight, 1, 3);

        grid.add(mkButton("exit", e -> {
            this.shutdown(null);
            Platform.exit();
        }), 3, 3);
        Scene scene = new Scene(grid, 500, 500);

        stage.setOnCloseRequest(this::shutdown);

        stage.setScene(scene);
        stage.show();
        ship.powerUp();
        refreshThread.start();
    }

    private Node mkFireBtn(Phaser phaserBank) {
        Button btn = new Button();
        btn.setText("fire");
        btn.setOnAction(event -> phaserBank.fire());
        return btn;
    }

    private GridPane mkGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

    private Gauge gauge(String title, Shield shield) {
        return GaugeBuilder.create().skinType(SKIN)//
                .title(title).subTitle("shield").unit("E").maxValue(shield.getMaxLoad().value()).build();
    }

    private Gauge gauge(String title, Phaser phaser) {
        return GaugeBuilder.create().skinType(SKIN)//
                .title(title).subTitle("phaser").unit("E").maxValue(phaser.getMaxLoad().value()).build();
    }

    private Gauge gauge(String title, WarpEngine engine) {
        return GaugeBuilder.create().skinType(SKIN)//
                .title(title).subTitle("warp").unit("P").maxValue(engine.getMax().value()).build();
    }

    private Gauge gauge(String title, Reactor reactor) {
        return GaugeBuilder.create().skinType(SKIN)//
                .title(title).subTitle("pwr").unit("P").maxValue(reactor.maxFlow().value()).build();
    }

    private void refresh() {
        frontShield.setValue(ship.shieldBow.getLoad().value());
        rearShield.setValue(ship.shieldStern.getLoad().value());
        phaserBank.setValue(ship.phaserBank.getLoad().value());
        reactor.setValue(ship.pwrMain.getFlow().value());

        warpLeft.setValue(ship.warpLeft.getPower().value());
        warpRight.setValue(ship.warpRight.getPower().value());
//        sparkler.setValue(ship.pwrMain.getFlow().value());
    }

    private Thread mkRefreshThread() {
        return new Thread() {
            @Override
            public void run() {
                super.run();
                for (;;) {
                    refresh();
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
    }

    private Button mkButton(String text, EventHandler<ActionEvent> action) {
        Button btn = new Button(text);
        btn.setOnAction(action);
        return btn;
    }

    private Slider mkReactorSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(ship.pwrMain.maxFlow().value());
        slider.setValue(ship.pwrMain.getFlow().value());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setOrientation(Orientation.VERTICAL);
        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            this.ship.pwrMain.setWantedFlow(Power.of(newValue.doubleValue()));
        };
        slider.valueProperty().addListener(listener);
        return slider;
    }

    private void shutdown(WindowEvent e) {
        this.ship.powerDown();
        refreshThread.stop();
    }
}
