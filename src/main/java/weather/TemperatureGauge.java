package weather;

import java.time.LocalDateTime;

/**
 * Calculates temperature at a particular date and a particular location.
 * Calculations based on this source http://www-das.uwyo.edu/~geerts/cwx/notes/chap16/geo_clim.html
 */
class TemperatureGauge {

    static final double TEMPERATURE_LAPSE_RATE = 9.8;
    static final double GLOBAL_ANNUAL_AVERAGE_TEMPERATURE = 27.0;

    static double fetch(ForecastBuilder fb) {
        return calculateTemperatureAtPositionAndDate(fb.location,fb.date);
    }

    static double calculateTemperatureAtPositionAndDate(Location position, LocalDateTime date) {
        double temperature = GLOBAL_ANNUAL_AVERAGE_TEMPERATURE;
        boolean positionIsInSouthernHemisphere = position.getLatitude() < 0;
        double latitude = Math.abs(position.getLatitude());

        temperature = adjustAnnualGlobalAverageToLatitude(temperature,latitude,positionIsInSouthernHemisphere);
        temperature = adjustAnnualGlobalAverageToElevation(position, temperature);

        double annualVariation = .13 * latitude * Math.pow(position.getKmsToSea(),0.2);
        double maxAverageAnnualTemperature = temperature + annualVariation/2;
        double minAverageAnnualTemperature = temperature - annualVariation/2;
        int currentMonth = date.getMonthValue();
        return calculateAverageTemperatureForMonth(maxAverageAnnualTemperature,minAverageAnnualTemperature,
                                                   currentMonth,getLowMonth(positionIsInSouthernHemisphere),
                                                   getHighMonth(positionIsInSouthernHemisphere));
    }

    static double adjustAnnualGlobalAverageToLatitude(double temperature, double latitude, boolean positionIsInSouthernHemisphere) {
        double temperatureAtLatitude = temperature;
        if (positionIsInSouthernHemisphere) {
            if (latitude >= 20) { //lapse rate in southern hemisphere occurs lower than 20 degrees
                temperatureAtLatitude = temperature - ((latitude - 20) * 0.63); //average temperature decreases by 0.63 degrees for each degree of latitude
            }
        } else if (latitude > 16) { //lapse rate in northern hemisphere occurs higher than 16 degrees
            temperatureAtLatitude  = temperature - ((latitude - 16) * 0.85);  //average temperature decreases by 0.85 degrees for each degree of latitude
        }
        return temperatureAtLatitude;
    }

    static double adjustAnnualGlobalAverageToElevation(Location location, double temperature) {
        double elevationAdjustment  = TEMPERATURE_LAPSE_RATE * (location.getElevation()/1000);
        return temperature - elevationAdjustment;
    }

    static int getLowMonth(boolean inSouthernHemisphere) {
        return inSouthernHemisphere ? 7 : 1;
    }

    static int getHighMonth(boolean inSouthernHemisphere) {
        return inSouthernHemisphere ? 1 : 7;
    }

    static double calculateAverageTemperatureForMonth(double max, double min, int currentMonth, int lowMonth, int highMonth) {
        double rate = (max - min)/6; //rate of change for the six month period from low to high or vice versa
        boolean maxToMin;
        if (highMonth < lowMonth) { //southern hemisphere
            maxToMin = currentMonth < lowMonth;
        } else { //northern hemisphere
            maxToMin = currentMonth >= highMonth;
        }
        if (maxToMin) { //start from the max and reduce by rate each month
            return max - ((currentMonth - highMonth)*rate);
        } else { //start from the min and increase by rate each month
            return min + ((currentMonth - lowMonth)*rate);
        }
    }


}
