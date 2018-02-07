package org.ully.enterprise.panel;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.energy.EnergyPanel;
import org.ully.enterprise.panel.environment.EnvironmentPanel;
import org.ully.enterprise.panel.helm.HelmPanel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Dashboard extends Application {

    private Starship ship = new Starship();
    private EnergyPanel energyPanel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Energy panel");
        energyPanel = new EnergyPanel(ship);
        Scene scene = new Scene(energyPanel, 900, 1000);
        stage.setOnCloseRequest(this::shutdown);
        stage.setScene(scene);
        stage.show();
        ship.powerUp();
        mkTimer().start();

        mkEnvironment(stage).show();
        mkHelm(stage).show();
    }

    long lastTimerCall = System.nanoTime();
    private HelmPanel helmPanel;
    private AnimationTimer mkTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 1_000_000L) {
                    energyPanel.refresh();
                    helmPanel.refresh();
                    lastTimerCall = now;
                }
            }
        };
        return timer;
    }

    private void shutdown(WindowEvent e) {
        this.ship.powerDown();
    }

    private Stage mkEnvironment(Stage opener) {
        EnvironmentPanel panel = new EnvironmentPanel(ship);
        Scene scene = new Scene(panel, 350, 300);

        Stage stage = new Stage();
        stage.setTitle("Environment emulation");
        stage.setScene(scene);

        // Set position of second window, related to primary window.
        stage.setX(opener.getX() + opener.getWidth());
        stage.setY(opener.getY());

        return stage;
    }

    private Stage mkHelm(Stage opener) {
        helmPanel = new HelmPanel(ship);
        Scene scene = new Scene(helmPanel, 350, 300);

        Stage stage = new Stage();
        stage.setTitle("helm panel");
        stage.setScene(scene);

        // Set position of second window, related to primary window.
        stage.setX(opener.getX() + opener.getWidth());
        stage.setY(400);

        return stage;
    }

}
