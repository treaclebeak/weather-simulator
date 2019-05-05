package weather;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  Usage: java Simulator <mandatory: name of csv file> <optional: months in future>
 *
 *  Input: file sourced from https://github.com/bahar/WorldCityLocations
 *
 *  Output: Prints weather data for 10 random cities at given date in this format
 *
 *     Sydney|-33.86,151.21,39|2015-12-23T05:02:12Z|Rain|+12.5|1004.3|97
 *
 *   where
 *
 *     Location is an optional label describing one or more positions,
 *     Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea level,
 *     Local time is an ISO8601 date time,
 *     Conditions is either Snow, Rain, Sunny,
 *     Temperature is in °C,
 *     Pressure is in hPa, and
 *     Relative humidity is a %.
 */
public class Simulator {

    public static void main(String[] args) {

        String fileName = getFileName(args);
        int monthsToAdd = getMonthsToAdd(args);
        LocalDateTime now = LocalDateTime.now().plusMonths(monthsToAdd);

        getLocations(fileName).map(location -> new ForecastBuilder().with( fb -> {
                                                                        fb.date = now;
                                                                        fb.location = location;
                                                                        fb.temperature = TemperatureGauge.fetch(fb);
                                                                        fb.humidity = HumidityGauge.fetch(fb);
                                                                        fb.pressure = PressureGauge.fetch(fb);
                                                                        fb.conditions = Conditions.fetch(fb);}))
                                .forEach(f -> System.out.println(f.location +"|"+
                                                                 formatDate(f.date) +"|"+
                                                                 f.conditions+"|"+
                                                                 Util.round(f.temperature)+"|"+
                                                                 Util.round(f.pressure)+"|"+
                                                                 Util.round(f.humidity)));


    }

    private static int getMonthsToAdd(String[] args) {
        if (args.length == 2) {
            return Integer.parseInt(args[1]);
        }
        return 0;
    }

    private static String getFileName(String[] args) {
        if (args.length > 0) {
            return args[0];
        }
        throw new IllegalArgumentException("Please include file name as first arg");
    }

    private static Stream<Location> getLocations(String fileName) {
        try {
            Stream<String> stream = Files.lines(Paths.get(fileName));
            List<Location> positions = stream.map(Location::fromCSVLine).collect(Collectors.toList());
            Collections.shuffle(positions);
            return positions.subList(0, 10).stream();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return Stream.empty();
        }
    }

    private static String formatDate(LocalDateTime date) {
        return DateTimeFormatter.ISO_INSTANT.format(date.toInstant(ZoneOffset.UTC));
    }

}
