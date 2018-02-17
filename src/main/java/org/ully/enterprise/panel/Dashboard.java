package org.ully.enterprise.panel;

import java.util.ArrayList;
import java.util.List;

import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.energy.EnergyPanel;
import org.ully.enterprise.panel.environment.EnvironmentPanel;
import org.ully.enterprise.panel.helm.HelmPanel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main dashboard of the Enterprise.
 */
public class Dashboard extends Application {

    private Enterprise ship = new Enterprise();
    private EnergyPanel energyPanel;
    private HelmPanel helmPanel;
    private boolean gridVisible = false;
    private List<Stage> stageList = new ArrayList<>();
    private long lastTimerCall = System.nanoTime();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Energy panel");
        energyPanel = new EnergyPanel(ship, gridVisible);
        Scene scene = new Scene(energyPanel, 900, 1000);
        stage.setOnCloseRequest(this::shutdown);
        stage.setScene(scene);
        stage.show();
        ship.powerUp();
        mkTimer().start();

        mkEnvironment(stage).show();
        mkHelm(stage).show();
    }

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
        stageList.forEach(Stage::close);
    }

    /**
     * Creates the stage for the environmental controls.
     *
     * @param opener
     *            the owning stage of the window.
     * @return the stage with the environment controller
     */
    private Stage mkEnvironment(Stage opener) {
        EnvironmentPanel panel = new EnvironmentPanel(ship, gridVisible);
        Scene scene = new Scene(panel, 350, 300);

        Stage stage = new Stage();
        stage.setTitle("Environment emulation");
        stage.setScene(scene);

        stageList.add(stage);

        stage.setX(opener.getX() + opener.getWidth());
        stage.setY(opener.getY());

        return stage;
    }

    /**
     * Creates the stage for the helm controls.
     *
     * @param opener
     *            the owning stage of the window.
     * @return the stage with the helm controller
     */
    private Stage mkHelm(Stage opener) {
        helmPanel = new HelmPanel(ship, gridVisible);
        Scene scene = new Scene(helmPanel, 350, 400);

        Stage stage = new Stage();
        stage.setTitle("helm panel");
        stage.setScene(scene);

        stageList.add(stage);

        stage.setX(opener.getX() + opener.getWidth());
        stage.setY(400);

        return stage;
    }

}
