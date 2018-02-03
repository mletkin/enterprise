package org.ully.enterprise.energy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.ully.enterprise.Component;
import org.ully.enterprise.Reactor;

/**
 * combination of supplier(s) and consumer(s) wired together.
 */
public class Circuit {

    public List<Reactor> supplier;

    public List<Component> consumer;

    public Stream<Component> getSupplier() {
        return Stream.concat(//
                streamOf(consumer).filter(s -> s.getDirection() == Component.Direction.OUT), //
                streamOf(supplier)//
        );
    }

    public Stream<Component> getConsumer() {
        return streamOf(consumer).filter(s -> s.getDirection() == Component.Direction.IN);
    }

    public Stream<Reactor> getReactors() {
        return streamOf(supplier);
    }

    public <T> Stream<T> streamOf(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream();
    }

}
