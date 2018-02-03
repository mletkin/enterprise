package org.ully.enterprise.energy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.ully.enterprise.Component;
import org.ully.enterprise.Reactor;

/**
 * Power circuit, connecting a list of components.
 */
public class Circuit {

    private List<Component> consumer = new ArrayList<>();

    public Circuit(Component... components) {
        if (components != null) {
            Stream.of(components).forEach(consumer::add);
        }
    }

    public Stream<Component> getSupplier() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.OUT);
    }

    public Stream<Component> getConsumer() {
        return consumer.stream().filter(s -> s.getDirection() == Component.Direction.IN);
    }

    public Stream<Reactor> getReactors() {
        return consumer.stream()//
                .filter(c -> Reactor.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Reactor) c);
    }

}
