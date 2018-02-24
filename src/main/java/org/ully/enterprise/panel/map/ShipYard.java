package org.ully.enterprise.panel.map;

import org.ully.enterprise.Starship;
import org.ully.enterprise.fleet.Enterprise;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Factory class for ship shapes.
 */
public class ShipYard {

    /**
     * Creates a shape for a star ship.
     *
     * @param ship
     *            The starship to represent
     * @param colour
     *            The color the shape shoud have
     * @return the shape representing the ship
     */
    public Shape mkShape(Starship ship, Color colour) {
        if (ship instanceof Enterprise) {
            return mkNcc1701(colour);
        }
        return null;
    }

    /**
     * Creates a shape for the third USS Enterprise.
     *
     * @param colour
     *            the colour the ship should have
     * @return the JavaFX shape
     */
    private Shape mkNcc1701(Color colour) {
        Circle saucer = new Circle();
        saucer.setCenterX(0.0f);
        saucer.setCenterY(0.0f);
        saucer.setRadius(20.6f);
        saucer.setFill(colour);

        Polyline fuselage = new Polyline();
        fuselage.getPoints().addAll(new Double[] { -4.4, 20.0, 4.4, 20.0, 4.0, 44.8, -4.0, 44.8, });
        fuselage.setStroke(colour);
        fuselage.setFill(colour);

        Rectangle pylon = new Rectangle();
        pylon.setX(-14.8);
        pylon.setY(36);
        pylon.setHeight(4.0);
        pylon.setWidth(33.6);
        pylon.setStroke(colour);
        pylon.setFill(colour);

        Rectangle leftNacelle = new Rectangle();
        leftNacelle.setX(-18.8);
        leftNacelle.setY(24.0);
        leftNacelle.setHeight(46.8);
        leftNacelle.setWidth(4.0);
        leftNacelle.setStroke(colour);
        leftNacelle.setFill(colour);

        Rectangle rightNacelle = new Rectangle();
        rightNacelle.setX(14.8);
        rightNacelle.setY(24.0);
        rightNacelle.setHeight(46.8);
        rightNacelle.setWidth(4.0);
        rightNacelle.setStroke(colour);
        rightNacelle.setFill(colour);

        Shape shape = Shape.union(saucer,
                Shape.union(fuselage, Shape.union(pylon, Shape.union(rightNacelle, leftNacelle))));
        shape.setFill(colour);
        return shape;
    }
}
