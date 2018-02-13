package org.ully.enterprise.panel;

import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Checker.ShortCircuitException;
import org.ully.enterprise.fleet.Potemkin;
import org.ully.enterprise.panel.energy.GenericEnergyPanel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Creates a generic Dashboard layout for a spaceship.
 */
public class GenericDashboard extends Application {

    private Starship ship = new Potemkin();
    private GenericEnergyPanel energyPanel;
    private boolean gridVisible = false;
    private long lastTimerCall = System.nanoTime();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Energy panel");
        energyPanel = new GenericEnergyPanel(ship, gridVisible);
        Scene scene = new Scene(energyPanel, 900, 1000);
        stage.setOnCloseRequest(this::shutdown);
        stage.setScene(scene);
        stage.show();
        try {
            ship.powerUp();
            mkTimer().start();
        } catch (ShortCircuitException e) {
            new Alert(AlertType.INFORMATION, e.msg(), ButtonType.OK).showAndWait();
        }
    }

    private AnimationTimer mkTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 1_000_000L) {
                    energyPanel.refresh();
                    lastTimerCall = now;
                }
            }
        };
    }

    private void shutdown(WindowEvent e) {
        this.ship.powerDown();
    }

}
