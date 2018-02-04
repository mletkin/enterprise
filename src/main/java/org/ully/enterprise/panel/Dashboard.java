package org.ully.enterprise.panel;

import org.ully.enterprise.Starship;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Dashboard extends Application {

    private Starship ship = new Starship();
    private EnergyPanel grid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Energy panel");

        grid = new EnergyPanel(ship);
        Scene scene = new Scene(grid, 900, 500);
        stage.setOnCloseRequest(this::shutdown);
        stage.setScene(scene);
        stage.show();
        ship.powerUp();
        mkTimer().start();
    }

    long lastTimerCall = System.nanoTime();
    private AnimationTimer mkTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 1_000_000L) {
                    grid.refresh();
                    lastTimerCall = now;
                }
            }
        };
        return timer;
    }

    private void shutdown(WindowEvent e) {
        this.ship.powerDown();
    }
}
