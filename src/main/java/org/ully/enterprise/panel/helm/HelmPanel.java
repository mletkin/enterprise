package org.ully.enterprise.panel.helm;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.util.Util;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Panel for helm of a ship with a single machine aggregate.
 */
public class HelmPanel extends GridPane implements Refreshable {

    private static final String UNIT_DISTANCE = "km";
    private static final String UNIT_SPEED = "m/s";
    private static final String UNIT_ACCELERATION = "m/s\u00B2";

    private Starship ship;
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
    public HelmPanel(Starship ship) {
        this.ship = ship;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        add(enginePanel = new EnginePanel(ship.drives().findFirst().orElse(null)), 0, 0, 3, 1);

        add(accGauge = mkAccGauge(ship), 0, 1);
        add(speedGauge = mkSpeedGauge(ship), 1, 1);
        add(distGauge = mkDistanceGauge(ship), 2, 1);

        add(bearingPanel = new CompassPanel(ship), 0, 2, 3, 1);

        add(Util.mkButton("stop", e -> ship.stop()), 0, 3);
    }

    private Gauge mkDistanceGauge(Starship ship) {
        return GaugeBuilder.create().skinType(SkinType.LCD) //
                .title("distance").unit(UNIT_DISTANCE).build();
    }

    private Gauge mkSpeedGauge(Starship ship) {
        return GaugeBuilder.create().skinType(SkinType.LCD) //
                .title("speed").unit(UNIT_SPEED).build();
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
