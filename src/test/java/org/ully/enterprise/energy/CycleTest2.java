package org.ully.enterprise.energy;

import static org.junit.Assert.assertEquals;
import static org.ully.enterprise.energy.TestUtil.c;

import org.junit.Test;
import org.ully.enterprise.units.Power;

public class CycleTest2 {

    @Test
    public void supplierCircuitOpenConsumerCircuitOpen() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con = new ConstantConsumer(Power.of(4));

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c(c(sup), c(con)));

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            assertEquals(5.0, sup.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(4.0, con.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(n * 0.4, con.load.value(), 0.01d);
        }
    }

    @Test
    public void supplierCircuitOpenConsumerCircuitClosed() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con = new ConstantConsumer(Power.of(4));

        Circuit c2 = c(con);
        c2.getPowerGateway().offline();

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c(c(sup), c2));

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            assertEquals(5.0, sup.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(0.0, con.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(0.0, con.load.value(), 0.01d);
        }
    }

    @Test
    public void supplierCircuitClosedConsumerCircuitOpen() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con = new ConstantConsumer(Power.of(4));

        Circuit c1 = c(sup);
        c1.getPowerGateway().offline();

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c(c1, c(con)));

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            assertEquals(5.0, sup.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(0.0, con.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(0.0, con.load.value(), 0.01d);
        }
    }


    @Test
    public void noSurplusFromSupplierCircuit() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con1 = new ConstantConsumer(Power.of(5));
        ConstantConsumer con2 = new ConstantConsumer(Power.of(5));

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c(c(sup, con1), c(con2)));

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            assertEquals(5.0, sup.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(n * 0.5, con1.getLoad().value(), 0.01d);
            assertEquals(5.0, con1.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(n * 0.0, con2.getLoad().value(), 0.01d);
            assertEquals(0.0, con2.getCurrentPowerFlow().value(), 0.01d);
        }
    }

    @Test
    public void surplusFromReactorCircuit() {
        ConstantSupplier r1 = new ConstantSupplier(Power.of(100));
        ConstantConsumer p1 = new ConstantConsumer(Power.of(5));
        ConstantConsumer p2 = new ConstantConsumer(Power.of(5));

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c(c(r1, p1), c(p2)));

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            assertEquals(100, r1.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(n * 0.5, p1.getLoad().value(), 0.01d);
            assertEquals(5.0, p1.getCurrentPowerFlow().value(), 0.01d);
            assertEquals(n * 0.5, p2.getLoad().value(), 0.01d);
            assertEquals(5.0, p2.getCurrentPowerFlow().value(), 0.01d);
        }
    }

}
