package org.ully.enterprise.panel.energy;

import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Switchable;
import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.PowerGateway;

import javafx.scene.layout.TilePane;

/**
 * Creates a generic panel for a power circuit.
 */
public class CircuitPanel extends TilePane {

    public CircuitPanel(Circuit circuit) {
        super();

        setHgap(10);
        setVgap(10);
        setPrefTileHeight(200);
        setPrefTileWidth(200);
        populate(circuit);
    }

    /**
     * Populate the Panel with instruments.
     *
     * @param circuit
     */
    private void populate(Circuit circuit) {
        circuit.getComponents().forEach(this::addPanel);
    }

    private void addPanel(Switchable component) {
        switch (component.getClass().getSimpleName()) {
        case "Shield":
            getChildren().add(new ShieldPane((Shield) component));
            break;
        case "Phaser":
            getChildren().add(new PhaserPane((Phaser) component));
            break;
        case "WarpEngine":
            getChildren().add(new EnginePane((WarpEngine) component));
            break;
        case "LifeSupport":
            getChildren().add(new LifeSupportPane((LifeSupport) component));
            break;
        case "Reactor":
            getChildren().add(new ReactorPane((Reactor) component));
            break;
        case "PowerGateway":
            getChildren().add(new GatewayPane((PowerGateway) component));
            break;
        }

    }
}
