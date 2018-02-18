package org.ully.enterprise.panel.map;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.Refreshable;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * Panel for Enterprise helm.
 */
public class MapPane extends Group implements Refreshable {

    private double xOffset = 0;
    private double yOffset = 0;

    private Starship ship;
    private Circle sprite = mkShip();
    private Group group = new Group();

    public MapPane(Starship ship) {
        super();
        this.ship = ship;
        getChildren().add(group);
        group.getChildren().add(sprite);
        group.getChildren().add(mkBtn());
    }

    private Node mkBtn() {
        Button btn = new Button();
        btn.setText("center");
        btn.setOnAction(e -> centerShip());
        return btn;
    }

    private Circle mkShip() {
        Circle circle = new Circle(10, Color.GREEN);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(1);
        return circle;
    }

    @Override
    public void refresh() {
        sprite.setCenterX(ship.position().x() + xOffset);
        sprite.setCenterY(-ship.position().y() + yOffset);
    }

    void centerShip() {
        xOffset = getScene().getWidth() / 2 - ship.position().x() ; //sprite.getCenterX();
        yOffset = getScene().getHeight() / 2 + ship.position().y() ; // sprite.getCenterY();
    }

}
