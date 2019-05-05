package weather;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Naive humidity calculation based on https://en.wikipedia.org/wiki/Dew_point
 */
class HumidityGauge {

    static double fetch(ForecastBuilder fb) {
        return calculate(fb.temperature);
    }

    static double calculate(double temperature) {
        return Math.min(100-(5*(temperature-getRandomDewpoint((int)temperature))),100);
    }

    static Integer getRandomDewpoint(int temp) {
        return ThreadLocalRandom.current().nextInt(0, Math.abs(temp)+1);
    }
}
