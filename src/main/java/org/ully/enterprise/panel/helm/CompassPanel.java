package org.ully.enterprise.panel.helm;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;
import org.ully.enterprise.units.Vector;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Compound panel for navigational controls.
 */
public class CompassPanel extends GridPane implements Refreshable {

    private Starship ship;
    private Gauge heading; // where the shp wants
    private Gauge bearing; // where the ship goes
    private Gauge home;
    private Label txtX;
    private Label txtY;

    /**
     * Creates a compound panel.
     *
     * @param ship
     *            the ship to monitor
     */
    public CompassPanel(Starship ship) {
        super();
        this.ship = ship;
        setAlignment(Pos.CENTER);
        add(heading = mkGauge("head"), 0, 0);
        add(bearing = mkGauge("bear"), 1, 0);
        add(home = mkGauge("home"), 2, 0);
        add(mkSlider(), 0, 1, 3, 1);

        add(new Label("x"), 0, 2);
        add(txtX = new Label(), 1, 2);
        add(new Label("y"), 0, 3);
        add(txtY = new Label(), 1, 3);
    }

    private Node mkSlider() {
        return SliderBuilder.horizontal() //
                .range(0, 360) //
                .value(ship.heading().deg()) //
                .withMajorTickUnit(90) //
                .onChange(value -> ship.heading(Vector.polar(value / 180 * Math.PI, 1))) //
                .get();
    }

    /**
     * Create a directional gauge.
     *
     * @return the newly created gauge instance
     */
    private Gauge mkGauge(String title) {
        Gauge gauge = GaugeBuilder.create().skinType(SkinType.FLAT)//
                .title(title)//
                .minValue(0).maxValue(359).autoScale(false).startAngle(180).build();
        return gauge;
    }

    @Override
    public void refresh() {
        heading.setValue(ship.heading().deg());
        bearing.setValue(ship.bearing().deg());
        home.setValue(Vector.ZERO.sub(ship.position()).deg());
        txtX.setText(String.format("%.3f", ship.position().x()));
        txtY.setText(String.format("%.3f", ship.position().y()));
    }

}
