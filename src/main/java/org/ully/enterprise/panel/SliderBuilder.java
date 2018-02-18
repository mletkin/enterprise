package org.ully.enterprise.panel;

import java.util.function.Consumer;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;

/**
 * Builder for Slider-Objects.
 */
public class SliderBuilder {

    private Slider slider = new Slider();

    /**
     * Prevent instantiation
     *
     * @param orientation
     *            the sliders orientation
     */
    private SliderBuilder(Orientation orientation) {
        slider.setOrientation(orientation);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
    }

    /**
     * Creates a builder for a vertical slider.
     *
     * @return
     */
    public static SliderBuilder vertical() {
        return new SliderBuilder(Orientation.VERTICAL);
    }

    /**
     * Creates a builder for a horizontal slider.
     *
     * @return
     */
    public static SliderBuilder horizontal() {
        return new SliderBuilder(Orientation.HORIZONTAL);
    }

    /**
     * Gets the built slider object.
     *
     * @return
     */
    public Slider get() {
        return slider;
    }

    public SliderBuilder range(double min, double max) {
        slider.setMin(min);
        slider.setMax(max);
        return this;
    }

    /**
     * Sets a double value consumer as change listener.
     *
     * @param listen
     * @return
     */
    public SliderBuilder onChange(Consumer<Double> listen) {
        slider.valueProperty().addListener(//
                (observable, oldValue, newValue) -> {
                    listen.accept(newValue.doubleValue());
                });
        return this;
    }

    public SliderBuilder withMajorTickUnit(double value) {
        slider.setMajorTickUnit(value);
        return this;
    }

    public SliderBuilder value(double value) {
        slider.setValue(value);
        return this;
    }

    public SliderBuilder blockIncrement(double value) {
        slider.setBlockIncrement(value);
        return this;
    }
}
