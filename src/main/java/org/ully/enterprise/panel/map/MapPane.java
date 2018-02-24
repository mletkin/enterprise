package org.ully.enterprise.panel.map;

import org.ully.enterprise.Starship;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.util.Util;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

/**
 * Pane to show the star ship's itinerary.
 */
public class MapPane extends Group implements Refreshable {

    private double xOffset = 0;
    private double yOffset = 0;

    private long zoom = 0;
    private double zoomFactor = 1;

    private Starship ship;
    private Shape sprite;

    /**
     * Creates a pane for a ship.
     *
     * @param ship
     */
    public MapPane(Starship ship) {
        super();
        this.ship = ship;
        sprite = new ShipYard().mkShape(ship, Enterprise.HULL);
        getChildren().add(sprite);
        getChildren().add(new StackPane());
    }

    Node mkCenterBtn() {
        return Util.mkButton("center", e -> centerShip());
    }

    @Override
    public void refresh() {
        sprite.setTranslateX((ship.position().x() + xOffset) * zoomFactor);
        sprite.setTranslateY((-ship.position().y() + yOffset) * zoomFactor);
        sprite.setRotate(ship.heading().deg());
        sprite.setScaleX(zoomFactor);
        sprite.setScaleY(zoomFactor);
    }

    /**
     * Position the ship in the center of the map.
     */
    void centerShip() {
        xOffset = (getScene().getWidth() / zoomFactor / 2 - ship.position().x());
        yOffset = (getScene().getHeight() / zoomFactor / 2 + ship.position().y());
    }

    /**
     * Zoom in or out and center the ship.
     *
     * @param n
     *            zoom by the nth power of two.
     */
    public void zoom(long n) {
        zoom += n;
        zoomFactor = Math.pow(2, zoom);
        centerShip();
    }

}
