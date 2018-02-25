package org.ully.enterprise.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public final class Util {

    private static final double EPSILON = 0.00000000001;

    private Util() {
        // prevent instantiation
    }

    /**
     * Creates a {@code null} tolerant stream.
     *
     * @param <T>
     *            Type of the stream elements
     * @param list
     *            a list to stream, might be {@code null}
     * @return a stream from the list
     */
    public static <T> Stream<T> streamOf(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream();
    }

    /**
     * Gets the maximum from the list.
     *
     * @param value
     *            list of long values
     * @return the maximum of the list
     */
    public static long max(long... value) {
        return LongStream.of(value).max().orElse(0);
    }

    /**
     * Checks a double value to be "about" zero.
     *
     * @param value
     *            floating point value to check
     * @return true, if the value is (about) zero
     */
    public static boolean isZero(double value) {
        return Math.abs(value) <= EPSILON;
    }

    /**
     * Returns all objects of a class in a stream as stream.
     *
     * @param <U> type of the objects in the input stream
     * @param <T> typ of the objects on the output stream
     * @param stream
     *            The stream to filter, may not be {@code null}.
     * @param clazz
     *            The class object specifying the desired class
     * @return The filtered, converted stream
     */
    public static <U, T> Stream<T> filter(Stream<U> stream, Class<T> clazz) {
        return stream.filter(c -> c == null || clazz.isAssignableFrom(c.getClass())) //
                .map(c -> (T) c);
    }

    /**
     * Creates a JavaFX button with a label and an action.
     *
     * @param text
     *            label to display on the button
     * @param action
     *            action to perform when clicked
     * @return a Button object
     */
    public static Button mkButton(String text, EventHandler<ActionEvent> action) {
        Button btn = new Button();
        btn.setText(text);
        btn.setOnAction(action);
        return btn;
    }
}
