package org.ully.enterprise.panel.environment;

import org.ully.enterprise.Component;
import org.ully.enterprise.environment.Stress;
import org.ully.enterprise.environment.StressEmulator;
import org.ully.enterprise.fleet.Enterprise;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;
import org.ully.enterprise.units.Power;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel for environment emulation for the enterprise.
 */
public class EnvironmentPanel extends GridPane implements Refreshable {

    private Slider sternPowerSlider = mkPowerSlider();
    private Slider sternTimeSlider = mkTimeSlider();

    private Slider bowPowerSlider = mkPowerSlider();
    private Slider bowTimeSlider = mkTimeSlider();

    /**
     * Create an environment emulating panel.
     *
     * @param ship
     *            the instance of the ship to monitor
     * @param gridVisible
     *            show grid lines for debugging
     */
    public EnvironmentPanel(Enterprise ship, boolean gridVisible) {
        super();

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
        setGridLinesVisible(gridVisible);

        add(new Label("stern shield"), 0, 0);
        add(new Label("pwr"), 0, 1);
        add(sternPowerSlider, 1, 1);
        add(new Label("msecr"), 0, 2);
        add(sternTimeSlider, 1, 2);
        add(mkBtn(ship.shieldStern, sternPowerSlider, sternTimeSlider), 2, 2);

        add(new Label("bow shield"), 0, 3);
        add(bowPowerSlider, 1, 3);
        add(new Label("msecr"), 0, 4);
        add(bowTimeSlider, 1, 4);
        add(mkBtn(ship.shieldBow, bowPowerSlider, bowTimeSlider), 2, 4);
    }

    private Slider mkTimeSlider() {
        return SliderBuilder.horizontal() //
                .range(100, 2000) //
                .withMajorTickUnit(500) //
                .blockIncrement(100) //
                .get();
    }

    private Slider mkPowerSlider() {
        return SliderBuilder.horizontal() //
                .range(0, 10) //
                .withMajorTickUnit(1) //
                .get();
    }

    private Button mkBtn(Component component, Slider powerSlider, Slider timeSlider) {
        Button btn = new Button("fire");
        EventHandler<ActionEvent> action = e -> stress(component, Power.of(powerSlider.getValue()),
                (long) timeSlider.getValue());
        btn.setOnAction(action);
        return btn;
    }

    private void stress(Component component, Power power, long msec) {
        Stress stress = new Stress(component);
        stress.setPower(power);
        stress.setMilliseconds(msec);
        StressEmulator.emulate(stress);
    }

    /**
     * Refresh all panels on the grid.
     */
    @Override
    public void refresh() {
        getChildren().stream() //
                .filter(c -> Refreshable.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Refreshable) c) //
                .forEach(Refreshable::refresh);
    }

}
