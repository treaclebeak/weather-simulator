package weather;

import org.junit.Test;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class PressureGaugeTest {

    @Test
    public void higherAltitudeShouldHaveLowerPressure() {
        double seaLevel = 0;
        double elevation = 3000;
        double temperature = 15.0;

        double pressureAtSeaLevel = PressureGauge.calculate(seaLevel, temperature);
        double pressureAboveSeaLevel = PressureGauge.calculate(elevation, temperature);

        assertThat(pressureAtSeaLevel, greaterThan(pressureAboveSeaLevel));
    }

    @Test
    public void lowerTemperatureShouldCreateHigherPressure() {
        double constantElevation = 10;
        double coldTemperature = 10.0;
        double hotTemperature = 30.0;

        double pressureCold = PressureGauge.calculate(constantElevation, coldTemperature);
        double pressureHot = PressureGauge.calculate(constantElevation, hotTemperature);

        assertThat(pressureCold, greaterThan(pressureHot));

    }
}
