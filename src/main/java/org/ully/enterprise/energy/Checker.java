package org.ully.enterprise.energy;

import java.util.HashSet;
import java.util.Set;

import org.ully.enterprise.Component;

/**
 * Check a energy circuit for short circuits.
 */
public class Checker {

    private Set<Component> checkList = new HashSet<>();

    /**
     * To be thrown, when a short circuit is found.
     */
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

    /**
     * Checks the circuit exception free for errors.
     *
     * @param circuit
     *            the power circuit to check
     * @return true, if there's no short circuit
     */
    public boolean isSafe(Circuit circuit) {
        try {
            check(circuit);
            return true;
        } catch (ShortCircuitException e) {
            return false;
        }
    }

    /**
     * Checks the circuit an throws an exception is a short circuit is found.
     *
     * @param circuit
     *            the circuit to check
     * @throws ShortCircuitException
     */
    public void check(Circuit circuit) throws ShortCircuitException {
        checkList.clear();
        checkIntern(circuit);
        circuit.getAllComponents().forEach(this::checkIntern);
    }

    private void checkIntern(Component component) {
        if (checkList.contains(component)) {
            throw new ShortCircuitException(component);
        }
        checkList.add(component);
    }

}
