package org.ully.enterprise.panel.energy;

import org.ully.enterprise.Enterprise;
import org.ully.enterprise.panel.Refreshable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Energy Panel for Enterprise components.
 */
public class EnergyPanel extends GridPane {

    /**
     * create the energy panel.
     *
     * @param ship
     *            the instance of the ship to monitor
     * @param gridVisible
     *            show grid lines for debugging
     */
    public EnergyPanel(Enterprise ship, boolean gridVisible) {
        super();

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
        setGridLinesVisible(gridVisible);

        // main power circuit
        add(new ShieldPane(ship.shieldBow), 0, 0);
        add(new ShieldPane(ship.shieldStern), 1, 0);

        add(new PhaserPane(ship.phaserPrime), 0, 1);
        add(new PhaserPane(ship.phaserSec), 1, 1);

        add(new ReactorPane(ship.pwrMain), 5, 0);
        add(new ReactorPane(ship.pwrAux), 6, 0);
        add(new EnginePane(ship.warpLeft), 0, 3);
        add(new EnginePane(ship.warpRight), 1, 3);

        // Life support circuit
        add(new ReactorPane(ship.pwrLife), 5, 3);
        add(new LifeSupportPane(ship.life), 6, 3);
    }

    /**
     * Refresh all panels on the grid.
     */
    public void refresh() {
        getChildren().stream() //
                .filter(c -> Refreshable.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Refreshable) c) //
                .forEach(Refreshable::refresh);
    }

}