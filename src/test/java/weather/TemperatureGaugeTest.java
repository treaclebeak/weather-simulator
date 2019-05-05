package weather;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TemperatureGaugeTest {

    @Test
    public void southernHemisphereTemperatureShouldDecreaseJanuaryToJuly() {
        double maxTemperature = 28.0;
        double minTemperature = 10.0;
        double rate = (maxTemperature - minTemperature)/6;
        for (int i = 1; i <= 7; i++) {
            double monthlyTemperature = TemperatureGauge.calculateAverageTemperatureForMonth(maxTemperature, minTemperature, i, 7, 1);
            assertThat(monthlyTemperature, is(equalTo(maxTemperature - ((i - 1)*rate))));
        }
    }

    @Test
    public void southernHemisphereTemperatureShouldIncreaseJulyToJanuary() {
        double maxTemperature = 28.0;
        double minTemperature = 10.0;
        double rate = (maxTemperature - minTemperature)/6;
        for (int i = 7; i <= 12; i++) {
            double monthlyTemperature = TemperatureGauge.calculateAverageTemperatureForMonth(maxTemperature, minTemperature, i, 7, 1);
            assertThat(monthlyTemperature, is(equalTo(minTemperature + ((i-7)*rate))));
        }
    }

    @Test
    public void northernHemisphereTemperatureShouldIncreaseJanuaryToJuly() {
        double maxTemperature = 28.0;
        double minTemperature = 10.0;
        double rate = (maxTemperature - minTemperature)/6;
        for (int i = 1; i <= 7; i++) {
            double monthlyTemperature = TemperatureGauge.calculateAverageTemperatureForMonth(maxTemperature, minTemperature, i, 1, 7);
            assertThat(monthlyTemperature, is(equalTo(minTemperature + ((i - 1)*rate))));
        }
    }

    @Test
    public void northernHemisphereTemperatureShouldDecreaseJulyToJanuary() {
        double maxTemperature = 28.0;
        double minTemperature = 10.0;
        double rate = (maxTemperature - minTemperature)/6;
        for (int i = 7; i <= 12; i++) {
            double monthlyTemperature = TemperatureGauge.calculateAverageTemperatureForMonth(maxTemperature, minTemperature, i, 1,7);
            assertThat(monthlyTemperature, is(equalTo(maxTemperature - ((i-7)*rate))));
        }
    }

    @Test
    public void lowMonthInSouthernHemisphereIsJuly() {
        assertThat(TemperatureGauge.getLowMonth(true),is(equalTo(7)));
    }

    @Test
    public void highMonthInSouthernHemisphereIsJanuary() {
        assertThat(TemperatureGauge.getHighMonth(true),is(equalTo(1)));
    }

    @Test
    public void lowMonthInNorthernHemisphereIsJanuary() {
        assertThat(TemperatureGauge.getLowMonth(false),is(equalTo(1)));
    }

    @Test
    public void highMonthInSouthernHemisphereIsJuly() {
        assertThat(TemperatureGauge.getHighMonth(false),is(equalTo(7)));
    }

    @Test
    public void temperatureAbove16NorthShouldBeAdjustedForLatitude() {
        double startingTemperature = 27.0;
        double adjustedTemperature = TemperatureGauge.adjustAnnualGlobalAverageToLatitude(startingTemperature,16.5,false);
        assertThat(adjustedTemperature,is(lessThan(startingTemperature)));
    }

    @Test
    public void temperatureBelow20SouthShouldBeAdjustedForLatitude() {
        double startingTemperature = 27.0;
        double adjustedTemperature = TemperatureGauge.adjustAnnualGlobalAverageToLatitude(startingTemperature,21,true);
        assertThat(adjustedTemperature,is(lessThan(startingTemperature)));
    }

    @Test
    public void temperatureShouldDecreaseWithElevation() {
        Location location = mock(Location.class);
        given(location.getElevation()).willReturn(1000.0);
        double startingTemperature = 30.0;
        double adjustedTemperature = TemperatureGauge.adjustAnnualGlobalAverageToElevation(location,startingTemperature);
        assertThat(adjustedTemperature,is(equalTo(startingTemperature - TemperatureGauge.TEMPERATURE_LAPSE_RATE)));
    }

    @Test
    public void temperatureAtSealLevelEquatorIsAlwaysGlobalAverage() {
        Location location = mock(Location.class);
        given(location.getLatitude()).willReturn(1.5);
        given(location.getElevation()).willReturn(0.0);
        double temperature = TemperatureGauge.calculateTemperatureAtPositionAndDate(location, LocalDateTime.now());
        assertThat(temperature,is(equalTo(TemperatureGauge.GLOBAL_ANNUAL_AVERAGE_TEMPERATURE)));
    }

}
