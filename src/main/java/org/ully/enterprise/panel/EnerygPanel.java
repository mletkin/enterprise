package org.ully.enterprise.panel;

import static java.util.Optional.ofNullable;

import org.ully.enterprise.Reactor;
import org.ully.enterprise.Starship;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EnerygPanel extends Application {

    private Starship ship = new Starship();

    private ReactorPane reactorMainPain = new ReactorPane(ship.pwrMain);
    private ReactorPane reactorAuxPain = new ReactorPane(ship.pwrAux);

    private PhaserPane phaserPain = new PhaserPane(ship.phaserBank);

    private ShieldPane frontShieldPain = new ShieldPane(ship.shieldBow);
    private ShieldPane rearShieldPain = new ShieldPane(ship.shieldStern);

    private EnginePane warpLeftPain = new EnginePane(ship.warpLeft);
    private EnginePane warpRightPain = new EnginePane(ship.warpRight);

    private LifeSupportPane lifePain = new LifeSupportPane(ship.life);
    private ReactorPane reactorLifePain = new ReactorPane(ship.pwrLife);

    // private Gauge sparkler = sparkGauge("pwrMain", ship.pwrMain);

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

        // main power circuit
        grid.add(frontShieldPain, 0, 0);
        grid.add(rearShieldPain, 1, 0);
        grid.add(phaserPain, 0, 1);
        grid.add(reactorMainPain, 5, 0);
        grid.add(reactorAuxPain, 6, 0);
        grid.add(warpLeftPain, 0, 3);
        grid.add(warpRightPain, 1, 3);

        // Life support circuit
        grid.add(reactorLifePain, 5, 3);
        grid.add(lifePain, 6, 3);

        Scene scene = new Scene(grid, 900, 500);

        stage.setOnCloseRequest(this::shutdown);
        stage.setScene(scene);
        stage.show();
        ship.powerUp();
        refreshThread.start();
    }

    private GridPane mkGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

    private void refresh() {
        ofNullable(warpLeftPain).ifPresent(EnginePane::refresh);
        ofNullable(warpRightPain).ifPresent(EnginePane::refresh);

        ofNullable(phaserPain).ifPresent(PhaserPane::refresh);

        ofNullable(reactorMainPain).ifPresent(ReactorPane::refresh);
        ofNullable(reactorAuxPain).ifPresent(ReactorPane::refresh);
        ofNullable(reactorLifePain).ifPresent(ReactorPane::refresh);

        ofNullable(frontShieldPain).ifPresent(ShieldPane::refresh);
        ofNullable(rearShieldPain).ifPresent(ShieldPane::refresh);

        ofNullable(lifePain).ifPresent(LifeSupportPane::refresh);
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

    private void shutdown(WindowEvent e) {
        this.ship.powerDown();
        refreshThread.stop();
    }
}
