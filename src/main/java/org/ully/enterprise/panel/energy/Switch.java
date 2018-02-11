package org.ully.enterprise.panel.energy;

import static java.util.Optional.ofNullable;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * Two state togge button.
 * <ul>
 * <li>Text and value of the buttons can be customized.
 * <li>The type of the value is parametrized.
 * <li>The {@code handler} is a consumer, that accepts a value of type T
 * <li>The on-Button is initially selected
 * </ul>
 *
 * @param <T>
 *            Type of the user data returned by the toggle group
 */
public class Switch<T> extends HBox {

    private ToggleGroup group = new ToggleGroup();

    private Consumer<T> handler = t -> {};

    private T onValue = null;
    private String onText = "on";

    private T offValue = null;
    private String offText = "off";

    public Switch<T> get() {
        group.selectedToggleProperty().addListener((ChangeListener<Toggle>) (ov, toggle, new_toggle) -> {
            if (new_toggle == null) {
                handler.accept(null);
            } else {
                handler.accept((T) group.getSelectedToggle().getUserData());
            }
        });

        addButton(onText, onValue, true);
        addButton(offText, offValue, false);
        return this;
    }

    /**
     * Add the handler for button switch.
     *
     * @param handler
     *            react on the user data value
     * @return the switch-Object
     */
    public Switch<T> with(Consumer<T> handler) {
        this.handler = handler;
        return this;
    }

    /**
     * Set name and return value of the "on"-button.
     *
     * @param text
     *            label text of the "on" button
     * @param value
     *            value to return when switcehd to "on"
     * @return the switch-Object
     */
    public Switch<T> withOn(String text, T value) {
        onText = ofNullable(text).orElse(onText);
        onValue = ofNullable(value).orElse(onValue);
        return this;
    }

    /**
     * Set name and return value of the "off"-button.
     *
     * @param text
     *            label text of the "off" button
     * @param value
     *            value to return when switcehd to "off"
     * @return the switch-Object
     */
    public Switch<T> withOff(String text, T value) {
        offText = ofNullable(text).orElse(offText);
        offValue = ofNullable(value).orElse(offValue);
        return this;
    }

    private void addButton(String text, T value, boolean selected) {
        ToggleButton btn = new ToggleButton();
        btn.setText(text);
        btn.setToggleGroup(group);
        btn.setUserData(value);
        btn.setSelected(selected);
        getChildren().add(btn);
    }

}
