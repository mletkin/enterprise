package org.ully.enterprise.panel.energy;

import static java.util.Optional.ofNullable;

import org.ully.enterprise.Component.Direction;
import org.ully.enterprise.energy.PowerGateway;
import org.ully.enterprise.panel.GaugeFactory;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Panel for a circuit energy exchange gateway.
 * <ul>
 * <li>Shows the energy flowing from or to the circuit through the gateway.
 * <li>When energy flows OUT of the circuit, the needle goes right.
 * <li>When energy flows INto the circuit the needle goes left.
 * <li>The scale is logarithmic from the center to the border.
 * </ul>
 * The scales maximum value is fixed at +/- 5 so the maximum power that may flow
 * would be around 148 Units.
 */
public class GatewayPane extends GridPane implements Refreshable {

    private static final int MAX = 5;
    private Gauge gauge;
    private PowerGateway gateway;

    /**
     * Ceates a pane for the given gateway.
     *
     * @param gateway
     *            the {@code PowerGateway}-object to attach
     */
    public GatewayPane(PowerGateway gateway) {
        this.gateway = gateway;

        setAlignment(Pos.CENTER);
        add(gauge = mkGauge(), 0, 0);
        add(mkOnlineSwitch(), 0, 1);
    }

    private Switch<Boolean> mkOnlineSwitch() {
        return new Switch<Boolean>() //
                .withOn("on", Boolean.TRUE) //
                .withOff("off", Boolean.FALSE) //
                .with(this::toggle)//
                .get();
    }

    private void toggle(Boolean value) {
        if (ofNullable(value).orElse(Boolean.FALSE)) {
            gateway.online();
        } else {
            gateway.offline();
        }
    }

    private Gauge mkGauge() {
        Gauge gauge = GaugeFactory.mkPowerGauge(gateway) //
                .minValue(-MAX).maxValue(MAX).build();
        gauge.setBarColor(gauge.getBarBackgroundColor());
        return gauge;
    }

    @Override
    public void refresh() {
        double value = gateway.getCurrentPowerFlow().equals(Power.ZERO) //
                ? 0
                : (gateway.getDirection() == Direction.OUT ? -1 : 1) * gateway.getCurrentPowerFlow().value();
        gauge.setValue(scale(value));
    }

    /**
     * Scale the signed energy value to the logarithmic scale.
     *
     * @param value
     * @return
     */
    private double scale(double value) {
        return Math.signum(value) * (Math.log(1 + Math.abs(value)));
    }
}
