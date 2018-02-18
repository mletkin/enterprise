package org.ully.enterprise.panel.energy;

import org.ully.enterprise.Starship;
import org.ully.enterprise.energy.Checker;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.util.Util;

import javafx.geometry.Insets;
import javafx.scene.layout.TilePane;

/**
 * Energy Panel for starship components.
 */
public class GenericEnergyPanel extends TilePane implements Refreshable {

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
    public GenericEnergyPanel(Starship ship) {
        super();

        setPadding(new Insets(5, 0, 5, 0));
        setVgap(4);
        setHgap(4);
        setPrefColumns(1);

        if (new Checker().isSafe(ship.powerSystem())) {
            ship.powerSystem().getSubCircuits().map(CircuitPanel::new).forEach(getChildren()::add);
        }
    }

    @Override
    public void refresh() {
        Util.filter(getChildren().stream(), Refreshable.class).forEach(Refreshable::refresh);
    }

}
