package org.ully.enterprise.panel.energy;

import java.util.Objects;

import org.ully.enterprise.Component;
import org.ully.enterprise.ImpulseEngine;
import org.ully.enterprise.LifeSupport;
import org.ully.enterprise.Phaser;
import org.ully.enterprise.Reactor;
import org.ully.enterprise.Shield;
import org.ully.enterprise.WarpEngine;
import org.ully.enterprise.energy.Circuit;
import org.ully.enterprise.energy.PowerGateway;
import org.ully.enterprise.panel.Refreshable;

import javafx.scene.Node;
import javafx.scene.layout.TilePane;

/**
 * Creates a generic panel for a power circuit.
 */
public class CircuitPanel extends TilePane implements Refreshable {

    public CircuitPanel(Circuit circuit) {
        super();

        setHgap(10);
        setVgap(10);
        setPrefTileHeight(200);
        setPrefTileWidth(200);
        // getChildren().add(new Label(circuit.getName()));
        populate(circuit);
    }

    /**
     * Populate the Panel with instruments.
     *
     * @param circuit
     *            circuit containing the components
     */
    private void populate(Circuit circuit) {
        circuit.getComponents().map(this::mkPanel).filter(Objects::nonNull).forEach(getChildren()::add);
    }

    /**
     * Creates a panel for a component.
     *
     * @param component
     *            component for which to create the panel
     * @return panel for the component
     */
    private Node mkPanel(Component component) {
        switch (component.getClass().getSimpleName()) {
        case "Shield":
            return new ShieldPane((Shield) component);
        case "Phaser":
            return new PhaserPane((Phaser) component);
        case "WarpEngine":
            return new EnginePane<WarpEngine>((WarpEngine) component);
        case "ImpulseEngine":
            return new EnginePane<ImpulseEngine>((ImpulseEngine) component);
        case "LifeSupport":
            return new LifeSupportPane((LifeSupport) component);
        case "Reactor":
            return new ReactorPane((Reactor) component);
        case "PowerGateway":
            return new GatewayPane((PowerGateway) component);
        }
        return null;
    }

    @Override
    public void refresh() {
        getChildren().stream() //
                .filter(Objects::nonNull) //
                .filter(c -> Refreshable.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Refreshable) c) //
                .forEach(Refreshable::refresh);
    }
}
