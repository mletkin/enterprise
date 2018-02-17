package org.ully.enterprise.panel.helm;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Vector;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Compound panel for navigational controls.
 */
public class CompassPanel extends GridPane implements Refreshable {

    private Starship ship;
    private Gauge heading; // where the shp wants
    private Gauge bearing; // where the ship goes
    private Gauge position;

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
        add(heading = mkGauge(), 0, 0);
        add(bearing = mkGauge(), 1, 0);
        add(position = mkGauge(), 2, 0);
        add(mkSlider(), 0, 1, 3, 1);
    }

    private Node mkSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(360);
        slider.setValue(ship.heading().deg());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(90);
        slider.setOrientation(Orientation.HORIZONTAL);

        ChangeListener<Number> listener = (ChangeListener<Number>) (observable, oldValue, newValue) -> {
            ship.heading(Vector.polar(newValue.doubleValue() / 180 * Math.PI, 1));
        };

        slider.valueProperty().addListener(//
                listener);
        return slider;
    }

    /**
     * Create a directional gauge.
     *
     * @return the newly created gauge instance
     */
    private Gauge mkGauge() {
        Gauge gauge = GaugeBuilder.create().skinType(SkinType.FLAT)//
                .minValue(0).maxValue(359).autoScale(false).startAngle(180).build();
        return gauge;
    }

    @Override
    public void refresh() {
        heading.setValue(ship.heading().deg());
        bearing.setValue(ship.bearing().deg());
        position.setValue(ship.position().deg());
    }

}
