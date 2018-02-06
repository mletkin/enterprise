package org.ully.enterprise.energy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.ully.enterprise.Component;

/**
 * Power circuit, connecting a list of components.
 */
public class Circuit {

    private List<Component> consumer = new ArrayList<>();

    /**
     * Creates a circuit containing the given Components.
     *
     * @param components
     */
    public Circuit(Component... components) {
        if (components != null) {
            Stream.of(components).forEach(consumer::add);
        }
    }

    /**
     * Gets all power supplying components in teh circuit.
     *
     * @return
     */
    public Stream<Component> getSupplier() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.OUT);
    }

    /**
     * Gets all power consuming components in teh circuit.
     *
     * @return
     */
    public Stream<Component> getConsumer() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.IN);
    }

    /**
     * Gets all components in teh circuit.
     *
     * @return
     */
    public Stream<Component> getComponents() {
        return consumer.stream();
    }

}
