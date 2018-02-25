package org.ully.enterprise.panel.energy;

import org.ully.enterprise.ImpulseEngine;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.util.Util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Energy Panel for Enterprise components.
 */
public class EnergyPanel extends GridPane implements Refreshable {

    /**
     * Create the energy panel.
     *
     * @param ship
     *            the instance of the ship to monitor
     */
    public EnergyPanel(Enterprise ship) {

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        // main power circuit
        add(new ShieldPane(ship.shieldBow), 0, 0);
        add(new ShieldPane(ship.shieldStern), 1, 0);

        add(new PhaserPane(ship.phaserPrime), 0, 1);
        add(new PhaserPane(ship.phaserSec), 1, 1);

        add(new GatewayPane(ship.mainPowerCircuit.getPowerGateway()), 5, 1);
        add(new GatewayPane(ship.lifePowerCircuit.getPowerGateway()), 6, 1);

        add(new EnginePane<ImpulseEngine>(ship.impulseLeft), 0, 3);
        add(new EnginePane<ImpulseEngine>(ship.impulseRight), 1, 3);

        add(new ReactorPane(ship.pwrMain), 5, 0);
        add(new ReactorPane(ship.pwrAux), 6, 0);

        // Life support circuit
        add(new ReactorPane(ship.pwrLife), 5, 3);
        add(new LifeSupportPane(ship.life), 6, 3);
    }

    @Override
    public void refresh() {
        Util.filter(getChildren().stream(), Refreshable.class).forEach(Refreshable::refresh);
    }

}
