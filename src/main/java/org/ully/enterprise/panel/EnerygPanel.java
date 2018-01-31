package org.ully.enterprise.panel;

import org.ully.enterprise.Phaser;
import org.ully.enterprise.Starship;
import org.ully.enterprise.units.Energy;

import eu.hansolo.medusa.Gauge;
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

public class EnerygPanel extends Application {

    private Starship ship = new Starship();
    private Gauge frontShield = shieldGauge("bow", ship.shieldBow.getMaxLoad());
    private Gauge rearShield = shieldGauge("stern", ship.shieldStern.getMaxLoad());
    private Gauge phaserBank = shieldGauge("phaser", ship.phaserBank.getMaxLoad());
    private Gauge reactor = shieldGauge("reactor", ship.reactor.maxFlow(1000));
    private Slider reactorSlider = mkReactorSlider();
    private Thread refreshThread = mkRefreshThread();;

    public static void main(String[] args) {
        launch(args);
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

        grid.add(mkButton("exit", this::shutdown), 3, 3);
        Scene scene = new Scene(grid, 500, 500);
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

    private Gauge shieldGauge(String title, Energy maxValue) {
        return GaugeBuilder.create().title(title).subTitle("").unit("E").maxValue(maxValue.value()).build();
    }

    private void refresh() {
        frontShield.setValue(ship.shieldBow.getLoad().value());
        rearShield.setValue(ship.shieldStern.getLoad().value());
        phaserBank.setValue(ship.phaserBank.getLoad().value());
        reactor.setValue(ship.reactor.getFlow(1000).value());
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
        slider.setMax(ship.reactor.maxFlow(1000).value());
        slider.setValue(ship.reactor.getFlow(1000).value());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setOrientation(Orientation.VERTICAL);
        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            this.ship.reactor.setFlow(Energy.of(newValue.doubleValue()));
        };
        slider.valueProperty().addListener(listener);
        return slider;
    }

    private void shutdown(ActionEvent e) {
        this.ship.powerDown();
        refreshThread.stop();
        Platform.exit();
    }
}
