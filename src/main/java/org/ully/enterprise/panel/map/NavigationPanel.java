package org.ully.enterprise.panel.map;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.util.Util;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The nav panel has a map in the enter and buttons on the top.
 */
public class NavigationPanel extends BorderPane implements Refreshable {

    private HBox buttons = new HBox();
    private MapPane mapPane;

    /**
     * Creates a navigation panel for the ship.
     *
     * @param ship
     *            star ship for which to create the map
     */
    public NavigationPanel(Starship ship) {
        mapPane = new MapPane(ship);
        setCenter(new ScrollPane(mapPane));
        setTop(buttons);

        buttons.getChildren().add(Util.mkButton("center", e -> mapPane.centerShip()));
        buttons.getChildren().add(Util.mkButton("zoom in", e -> mapPane.zoom(1)));
        buttons.getChildren().add(Util.mkButton("zoom out", e -> mapPane.zoom(-1)));
    }

    @Override
    public void refresh() {
        mapPane.refresh();
    }
}
