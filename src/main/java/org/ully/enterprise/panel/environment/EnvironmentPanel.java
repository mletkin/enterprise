package org.ully.enterprise.panel.environment;

import org.ully.enterprise.Component;
import org.ully.enterprise.Shield;
import org.ully.enterprise.Starship;
import org.ully.enterprise.environment.Stress;
import org.ully.enterprise.environment.StressEmulator;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.panel.SliderBuilder;
import org.ully.enterprise.units.Power;
import org.ully.enterprise.util.Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel for environment emulation for a starship.
 */
public class EnvironmentPanel extends GridPane implements Refreshable {

    /**
     * Creates an environment emulation panel.
     *
     * @param ship
     *            the instance of the ship to stress
     */
    public EnvironmentPanel(Starship ship) {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        mkShieldsControls(ship);
    }

    private void mkShieldsControls(Starship ship) {
        MkShield maker = new MkShield();
        Util.filter(ship.powerSystem().getAllComponents(), Shield.class) //
                .forEach(maker::addShield);
    }

    /**
     * Helper class to keep track of the shield rows.
     */
    class MkShield {

        private int row = 0;

        private void addShield(Shield shield) {
            Slider pwrSlider = mkPowerSlider();
            Slider timeSlider = mkTimeSlider();

            add(new Label("shield " + shield.getName()), 0, row);
            add(new Label("pwr"), 0, row + 1);
            add(pwrSlider, 1, row + 1);
            add(new Label("msec"), 0, row + 2);
            add(timeSlider, 1, row + 2);
            add(mkBtn(shield, pwrSlider, timeSlider), 2, row + 2);
            row += 3;
        }
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
        Util.filter(getChildren().stream(), Refreshable.class).forEach(Refreshable::refresh);
    }

}
