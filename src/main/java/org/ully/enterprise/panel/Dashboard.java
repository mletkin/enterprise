package org.ully.enterprise.panel;

import java.util.ArrayList;
import java.util.List;

import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.energy.EnergyPanel;
import org.ully.enterprise.panel.environment.EnvironmentPanel;
import org.ully.enterprise.panel.helm.HelmPanel;
import org.ully.enterprise.panel.map.NavigationPanel;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Display the dashboard of the Enterprise.
 */
public class Dashboard extends Application {

    private Enterprise ship = new Enterprise();
    private EnergyPanel energyPanel;
    private HelmPanel helmPanel;
    private NavigationPanel mapPanel;
    private long lastTimerCall = System.nanoTime();
    private List<Stage> stageList = new ArrayList<>();
    private List<Refreshable> panelList = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Energy panel");
        panelList.add(energyPanel = new EnergyPanel(ship));
        Scene scene = new Scene(energyPanel, 900, 1000);
        stage.setOnCloseRequest(this::shutdown);
        stage.setScene(scene);
        stage.show();
        ship.powerUp();
        mkTimer().start();

        stageList.add(mkEnvironment(stage));
        stageList.add(mkHelm(stage));
        stageList.add(mkNav(stage));
        stageList.forEach(Stage::show);
    }

    private AnimationTimer mkTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 1_000_000L) {
                    panelList.forEach(Refreshable::refresh);
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
        EnvironmentPanel panel = new EnvironmentPanel(ship);
        Stage stage = new Stage();
        stage.setTitle("Environment emulation");
        stage.setScene(new Scene(panel, 350, 300));
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
        panelList.add(helmPanel = new HelmPanel(ship));
        Stage stage = new Stage();
        stage.setTitle("helm panel");
        stage.setScene(new Scene(helmPanel, 350, 400));
        stage.setX(opener.getX() + opener.getWidth());
        stage.setY(400);
        return stage;
    }

    /**
     * Creates the stage for the navigation controls.
     *
     * @param opener
     *            the owning stage of the window.
     * @return the stage with map window
     */
    private Stage mkNav(Stage opener) {
        panelList.add(mapPanel = new NavigationPanel(ship));
        Stage stage = new Stage();
        stage.setTitle("star map");
        stage.setScene(new Scene(mapPanel, 350, 400, Color.BLACK));
        stage.setX(opener.getX() - 350);
        stage.setY(0);
        return stage;
    }
}
