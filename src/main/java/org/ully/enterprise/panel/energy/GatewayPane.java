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

public class GatewayPane extends GridPane implements Refreshable {

    private static final int MAX = 5;
    private Gauge gauge;
    private PowerGateway gateway;

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
        gauge.setValue(transform(value));
    }

    private double transform(double value) {
        return Math.signum(value) * (Math.log(1 + Math.abs(value)));
    }
}
