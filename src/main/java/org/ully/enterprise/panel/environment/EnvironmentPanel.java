package org.ully.enterprise.panel.environment;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ully.enterprise.Component;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Starship;
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

    /**
     * Create an environment emulating panel.
     *
     * @param ship
     *            the instance of the ship to monitor
     * @param gridVisible
     *            show grid lines for debugging
     */
    public EnvironmentPanel(Enterprise ship) {
        super();

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        mkShieldsControls(ship);
    }

    private void mkShieldsControls(Starship ship) {
        int row = 0;
        for (Shield shield : getShields(ship).collect(Collectors.toList())) {
            row = mkShieldControl(row++, shield);
        }
    }

    private Stream<Shield> getShields(Starship ship) {
        return ship.powerSystem().getAllComponents() //
                .filter(c -> Shield.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Shield) c);
    }

    private int mkShieldControl(int row, Shield shield) {
        System.out.println("row " + row);
        Slider pwrSlider = mkPowerSlider();
        Slider timeSlider = mkTimeSlider();

        add(new Label("shield " + shield.getName()), 0, row);
        add(new Label("pwr"), 0, row + 1);
        add(pwrSlider, 1, row + 1);
        add(new Label("msec"), 0, row + 2);
        add(timeSlider, 1, row + 2);
        add(mkBtn(shield, pwrSlider, timeSlider), 2, row + 2);
        return row + 3;
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

    @Override
    public void refresh() {
        getChildren().stream() //
                .filter(c -> Refreshable.class.isAssignableFrom(c.getClass())) //
                .map(c -> (Refreshable) c) //
                .forEach(Refreshable::refresh);
    }

}
