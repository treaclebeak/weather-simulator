package weather;

/**
 * Extremely simple heuristics to determine weather condition from temperature, humidity and air pressure
 */
class Conditions {

    static final String SNOW = "Snow";
    static final String RAIN = "Rain";
    static final String SUN = "Sunny";

    static String fetch(ForecastBuilder fb) {
        return calculate(fb.temperature,fb.humidity,fb.pressure);
    }

    static String calculate(double temperature, double humidity, double pressure) {
        if (lowPressure(pressure)) {
            return freezing(temperature) ? SNOW : RAIN;
        } else {
            return highHumidity(humidity) ? RAIN : SUN;
        }
    }

    static boolean lowPressure(double pressure) {
        return pressure < 1000;
    }

    static boolean freezing(double temp) {
        return temp < 0;
    }

    static boolean highHumidity(double humidity) {
        return humidity > 80;
    }
}
