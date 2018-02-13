package org.ully.enterprise.energy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ully.enterprise.Component;

public class Checker {

    private Set<Component> checkList = new HashSet<>();

    public class ShortCircuitException extends RuntimeException {

        private Component c;

        ShortCircuitException(Component c) {
            this.c = c;
        }

        public String msg() {
            if (c == null) {
                return String.format("Short circuit caused by unknown component");
            }
            return String.format("Short circuit caused by %s '%s'", c.getClass().getSimpleName(), c.getName());
        }
    }

    public boolean isSafe(Stream<Circuit> circuits) {
        try {
            check(circuits);
            return true;
        } catch (ShortCircuitException e) {
            return false;
        }
    }

    /**
     * Is the circuit safe?
     *
     * @param circuit
     *            the power circuit to check
     * @return true, if there's no short circuit
     */
    public boolean isSafe(Circuit circuit) {
        return isSafe(Stream.of(circuit));
    }

    public void check(Stream<Circuit> circuits) throws ShortCircuitException {
        List<Circuit> liste = circuits.collect(Collectors.toList());
        checkList.clear();
        liste.forEach(this::checkIntern);
        liste.stream().flatMap(Circuit::getAllComponents).forEach(this::checkIntern);
    }

    public void check(Circuit circuit) throws ShortCircuitException {
        checkList.clear();
        check(Stream.of(circuit));
    }

    private void checkIntern(Component component) {
        if (checkList.contains(component)) {
            throw new ShortCircuitException(component);
        }
        checkList.add(component);
    }

}
