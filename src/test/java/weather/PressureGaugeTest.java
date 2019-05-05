package weather;

import org.junit.Test;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class PressureGaugeTest {

    @Test
    public void higherAltitudeShouldCreateHigherPressure() {
        final double seaLevel = 0;
        final double elevation = 3000;
        final double temperature = 15.0;

        final double pressureAtSea = PressureGauge.calculate(seaLevel, temperature);
        final double pressureOnMountain = PressureGauge.calculate(elevation, temperature);

        assertThat(pressureAtSea, greaterThan(pressureOnMountain));
    }

    @Test
    public void lowerTemperatureShouldCreateHigherPressure() {
        final double constantElevation = 10;
        final double coldTemperature = 10.0;
        final double hotTemperature = 30.0;

        final double pressureCold = PressureGauge.calculate(constantElevation, coldTemperature);
        final double pressureHot = PressureGauge.calculate(constantElevation, hotTemperature);

        assertThat(pressureCold, greaterThan(pressureHot));

    }
}
