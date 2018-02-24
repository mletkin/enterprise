package org.ully.enterprise.panel.map;

import org.ully.enterprise.Starship;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.Refreshable;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.shape.Shape;

/**
 * Panel for star ship's helm.
 */
public class MapPane extends Group implements Refreshable {

    private double xOffset = 0;
    private double yOffset = 0;

    private Starship ship;
    private Shape sprite;
    private Group group = new Group();

    /**
     * Creates a panel for a ship.
     *
     * @param ship
     */
    public MapPane(Starship ship) {
        super();
        this.ship = ship;
        sprite = new ShipYard().mkShape(ship, Enterprise.HULL);

        getChildren().add(group);
        group.getChildren().add(sprite);
        group.getChildren().add(mkCenterBtn());
    }

    private Node mkCenterBtn() {
        Button btn = new Button();
        btn.setText("center");
        btn.setOnAction(e -> centerShip());
        return btn;
    }

    @Override
    public void refresh() {
        sprite.setTranslateX(ship.position().x() + xOffset);
        sprite.setTranslateY(-ship.position().y() + yOffset);
        sprite.setRotate(ship.heading().deg());
    }

    /**
     * Position the ship in the center of the map.
     */
    private void centerShip() {
        xOffset = getScene().getWidth() / 2 - ship.position().x();
        yOffset = getScene().getHeight() / 2 + ship.position().y();
    }

}
