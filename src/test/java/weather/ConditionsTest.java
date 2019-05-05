package weather;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConditionsTest {

    @Test
    public void lowPressureIsBelow1KhPa() {
        assertThat(Conditions.lowPressure(999.99),is(equalTo(true)));
    }

    @Test
    public void freezingIsBelowZero() {
        assertThat(Conditions.freezing(-0.1),is(equalTo(true)));
    }

    @Test
    public void highHumidityisAbove80Percentt() {
        assertThat(Conditions.highHumidity(80.1),is(equalTo(true)));
    }

    @Test
    public void lowPressureFreezingIsSnow() {
        assertThat(Conditions.calculate(-1,50.0,999),is(equalTo(Conditions.SNOW)));
    }

    @Test
    public void lowPressureAboveFreezingIsSun() {
        assertThat(Conditions.calculate(-1,50.0,1021),is(equalTo(Conditions.SUN)));
    }

    @Test
    public void highPressureLowHumidityIsSun() {
        assertThat(Conditions.calculate(30,50.0,1021),is(equalTo(Conditions.SUN)));
    }

    @Test
    public void highPressureHighHumidityIsRain() {
        assertThat(Conditions.calculate(30,81.0,1021),is(equalTo(Conditions.RAIN)));
    }
}
