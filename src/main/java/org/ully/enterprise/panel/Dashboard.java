package org.ully.enterprise.panel;

import org.ully.enterprise.Starship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Dashboard extends Application {

    private Starship ship = new Starship();
    private EnergyPanel grid;
    private Thread refreshThread = mkRefreshThread();

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
        refreshThread.start();
    }

    private Thread mkRefreshThread() {
        return new Thread() {
            @Override
            public void run() {
                super.run();
                for (;;) {
                    grid.refresh();
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

    private void shutdown(WindowEvent e) {
        this.ship.powerDown();
        refreshThread.stop();
    }
}
