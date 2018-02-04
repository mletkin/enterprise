package org.ully.enterprise.panel;

import org.ully.enterprise.Component;
import org.ully.enterprise.Starship;
import org.ully.enterprise.units.Energy;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * Panel for envoronment emulation for a starship.
 */
public class EnvironmentPanel extends GridPane implements Refreshable {

    private Slider sternSlider = mkSlider();
    private Slider bowSlider = mkSlider();
    private Starship ship;

    /**
     * create panel.
     *
     * @param starship
     */
    public EnvironmentPanel(Starship ship) {
        super();

        this.ship = ship;
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        add(new Label("stern shield"), 0, 0);
        add(sternSlider, 1, 0);
        add(mkBtn(ship.shieldStern, sternSlider), 2, 0);

        add(new Label("bow shield"), 0, 1);
        add(bowSlider, 1, 1);
        add(mkBtn(ship.shieldBow, bowSlider), 2, 1);

    }

    private Slider mkSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(2);
        slider.setBlockIncrement(1);
        slider.setOrientation(Orientation.HORIZONTAL);
        return slider;
    }

    private Button mkBtn(Component component, Slider slider) {
        Button btn = new Button("fire");
        btn.setOnAction(e -> component.load(Energy.of(-slider.getValue()), 1000));
        return btn;
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
