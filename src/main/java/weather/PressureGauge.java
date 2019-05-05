package weather;

/**
 * Calculate air pressure from elevation and temperature
 *
 * https://en.wikipedia.org/wiki/Atmospheric_pressure
 * https://keisan.casio.com/exec/system/1224579725
 */
class PressureGauge {

    static double fetch(ForecastBuilder fb) {
        return calculate(fb.location.getElevation(),fb.temperature);
    }

    static double calculate(double elevation, double temperature) {
        double SEA_LEVEL_PRESSURE = 101325.0;
        double TEMPERATURE_LAPSE_RATE = 0.0065;
        double STANDARD_TEMPERATURE = 288.15;
        double EXPONENT = 5.257;
        double standardPressureAtElevation = SEA_LEVEL_PRESSURE*Math.pow(1 - (TEMPERATURE_LAPSE_RATE*elevation/STANDARD_TEMPERATURE),EXPONENT);
        double pressureAtTemperature = standardPressureAtElevation * STANDARD_TEMPERATURE / convertToKelvin(temperature);
        return toHPa(pressureAtTemperature);

    }

    static double convertToKelvin(double temperature) {
        return 273.15+temperature;
    }

    private static double toHPa(double pressureInPa) {
        return Math.round(pressureInPa) / 100.0;
    }


}
