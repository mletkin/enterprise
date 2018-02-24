package org.ully.enterprise.panel.helm;

import org.ully.enterprise.Starship;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.Refreshable;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Panel for Enterprise helm.
 */
public class HelmPanel extends GridPane implements Refreshable {

    private static final String UNIT_ACCELERATION = "m/s\u00B2";
    private Enterprise ship;
    private Gauge speedGauge;
    private Gauge accGauge;
    private Gauge distGauge;
    private CompassPanel bearingPanel;
    private EnginePanel enginePanel;

    /**
     * Creates the helm panel.
     *
     * @param ship
     *            the instance of the ship to monitor
     */
    public HelmPanel(Enterprise ship) {
        super();
        this.ship = ship;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        add(enginePanel = new EnginePanel(ship.drive), 0, 0, 3, 1);

        add(accGauge = mkAccGauge(ship), 0, 1);
        add(speedGauge = mkSpeedGauge(ship), 1, 1);
        add(distGauge = mkDistanceGauge(ship), 2, 1);

        add(bearingPanel = new CompassPanel(ship), 0, 2, 3, 1);

        add(mkStopButton(), 0, 3);
    }

    private Node mkStopButton() {
        Button btn = new Button();
        btn.setText("Stop");
        btn.setOnAction(e -> ship.stop());
        return btn;
    }

    private Gauge mkDistanceGauge(Starship ship) {
        return GaugeBuilder.create().skinType(SkinType.LCD) //
                .title("distance").unit("km").build();
    }

    private Gauge mkSpeedGauge(Starship ship) {
        return GaugeBuilder.create().skinType(SkinType.LCD) //
                .title("speed").unit("m/s").build();
    }

    private Gauge mkAccGauge(Starship ship) {
        return GaugeBuilder.create().skinType(SkinType.LCD) //
                .title("acceleration").unit(UNIT_ACCELERATION).build();
    }

    @Override
    public void refresh() {
        speedGauge.setValue(ship.speed());
        accGauge.setValue(ship.acceleration);
        distGauge.setValue(ship.position().abs() / 1000.0);

        bearingPanel.refresh();
        enginePanel.refresh();
    }

}
