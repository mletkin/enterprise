package org.ully.enterprise.energy;

import org.junit.Assert;
import org.junit.Test;
import org.ully.enterprise.units.Power;

public class CycleTest2 {

    @Test
    public void supplierCircuitonsumerCircuit() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        Circuit c1 = new Circuit(sup);

        ConstantConsumer con = new ConstantConsumer(Power.of(4));
        Circuit c2 = new Circuit(con);

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c1, c2);

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            Assert.assertEquals(5.0, sup.getCurrentPowerFlow().value(), 0.01d);
            Assert.assertEquals(4.0, con.getCurrentPowerFlow().value(), 0.01d);
            Assert.assertEquals(n * 0.4, con.load.value(), 0.01d);
        }
    }

    @Test
    public void noSurplusFromSupplierCircuit() {
        ConstantSupplier sup = new ConstantSupplier(Power.of(5));
        ConstantConsumer con1 = new ConstantConsumer(Power.of(5));
        Circuit c1 = new Circuit(sup, con1);

        ConstantConsumer con2 = new ConstantConsumer(Power.of(5));
        Circuit c2 = new Circuit(con2);

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c1, c2);

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            Assert.assertEquals(5.0, sup.getCurrentPowerFlow().value(), 0.01d);
            Assert.assertEquals(n * 0.5, con1.getLoad().value(), 0.01d);
            Assert.assertEquals(5.0, con1.getCurrentPowerFlow().value(), 0.01d);
            Assert.assertEquals(n * 0.0, con2.getLoad().value(), 0.01d);
            Assert.assertEquals(0.0, con2.getCurrentPowerFlow().value(), 0.01d);
        }
    }

    @Test
    public void surplusFromReactorCircuit() {
        ConstantSupplier r1 = new ConstantSupplier(Power.of(100));
        ConstantConsumer p1 = new ConstantConsumer(Power.of(5));
        Circuit c1 = new Circuit(r1, p1);

        ConstantConsumer p2 = new ConstantConsumer(Power.of(5));
        Circuit c2 = new Circuit(p2);

        PowerFlowEmulator emulator = PowerFlowEmulator.get(100).with(c1, c2);

        for (int n = 1; n <= 5; n++) {
            emulator.calculateSingleCycle();

            Assert.assertEquals(100, r1.getCurrentPowerFlow().value(), 0.01d);
            Assert.assertEquals(n * 0.5, p1.getLoad().value(), 0.01d);
            Assert.assertEquals(5.0, p1.getCurrentPowerFlow().value(), 0.01d);
            Assert.assertEquals(n * 0.5, p2.getLoad().value(), 0.01d);
            Assert.assertEquals(5.0, p2.getCurrentPowerFlow().value(), 0.01d);
        }
    }

}