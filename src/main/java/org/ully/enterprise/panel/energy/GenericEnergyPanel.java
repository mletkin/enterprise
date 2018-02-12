package org.ully.enterprise.panel.energy;

import java.util.Objects;

import org.ully.enterprise.Starship;
import org.ully.enterprise.panel.Refreshable;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

/**
 * Energy Panel for starship components.
 */
public class GenericEnergyPanel extends TilePane {

    /**
     * Creates the energy panel.
     * <p>
     * Each circuit (and sub circuit) has it's own curcuit panel.
     *
     * @param ship
     *            the instance of the ship to monitor
     * @param gridVisible
     *            show grid lines for debugging
     */
    public GenericEnergyPanel(Starship ship, boolean gridVisible) {
        super();

        setPadding(new Insets(5, 0, 5, 0));
        setVgap(4);
        setHgap(4);
        setPrefColumns(1);

        ship.getCircuits().map(CircuitPanel::new).forEach(getChildren()::add);
    }

    /**
     * Refresh all panels and sub-panels.
     */
    public void refresh() {
        getChildren().stream() //
                .filter(Objects::nonNull)//
                .filter(n -> Pane.class.isAssignableFrom(n.getClass())) //
                .map(n -> (Pane) n)//
                .map(Pane::getChildren)//
                .flatMap(ObservableList::stream) //
                .filter(c -> Refreshable.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Refreshable) c) //
                .forEach(Refreshable::refresh);
    }

}
