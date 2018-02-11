package org.ully.enterprise.panel.energy;

import org.ully.enterprise.Component.Direction;
import org.ully.enterprise.energy.PowerGateway;
import org.ully.enterprise.panel.Refreshable;
import org.ully.enterprise.units.Power;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Panel for a circuit energy exchange gateway.
 * <ul>
 * <li>Shows the energy flowing from or to the circuit through the gateway.
 * <li>When energy flows OUT of the circuit, the needle goes left.
 * <li>When energy flows INto the circuit the needle goes right.
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
     */
    public GatewayPane(PowerGateway gateway) {
        super();
        this.gateway = gateway;
        setAlignment(Pos.CENTER);
        add(mkGauge(), 0, 0);
    }

    private Gauge mkGauge() {
        gauge = GaugeBuilder.create().skinType(SkinType.INDICATOR)//
                .title("gate").subTitle("pwr").unit(Power.SYMBOL).minValue(-MAX).maxValue(MAX).build();
        gauge.setBarColor(gauge.getBarBackgroundColor());
        return gauge;
    }

    @Override
    public void refresh() {
        double value = gateway.getCurrentPowerFlow().equals(Power.ZERO) //
                ? 0
                : (gateway.getDirection() == Direction.IN ? -1 : 1) * gateway.getCurrentPowerFlow().value();
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
