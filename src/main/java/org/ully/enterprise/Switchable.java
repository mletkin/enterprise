package org.ully.enterprise;

public interface Switchable {

    /**
     * Toggles the online state.
     */
    void toggle();

    /**
     * Is the component online?
     *
     * @return true iff the component is connected
     */
    boolean isOnline();

    default void online() {
        if (!isOnline()) {
            toggle();
        }
    }

    default void offline() {
        if (isOnline()) {
            toggle();
        }
    }

}