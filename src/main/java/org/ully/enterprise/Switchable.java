package org.ully.enterprise;

/**
 * Interface for components that may be dis-/connected.
 */
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

    /**
     * Sets the engine online.
     */
    default void online() {
        if (!isOnline()) {
            toggle();
        }
    }

    /**
     * Sets the engine online.
     */
    default void offline() {
        if (isOnline()) {
            toggle();
        }
    }

}
