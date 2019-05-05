package weather;

/**
 * Represents a location on earth
 */
public class Location {

    private int kmsToSea;
    private String name;
    private double latitude;
    private double longitude;
    private double elevation;

    private Location(String name, double latitude, double longitude, double elevation, int kmsToSea) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.kmsToSea = kmsToSea;
    }

    static Location fromCSVLine(String csv) {
        String[] array = csv.split(";");
        String name = array[2].replaceAll("\"", "");
        String latitude = array[3].replaceAll("\"", "");
        String longitude = array[4].replaceAll("\"", "");
        String elevation = array[5].replaceAll("\"", "");
        return new Location(name,
                            Double.valueOf(latitude),
                            Double.valueOf(longitude),
                            Double.valueOf(elevation),1);
    }

    int getKmsToSea() {
        return kmsToSea;
    }


    double getLatitude() {
        return latitude;
    }


    double getElevation() {
        return elevation;
    }

    String getName() {
        return name;
    }

    double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name + "|"+Util.round(latitude)+","+Util.round(longitude) +","+ Util.round(elevation);
    }


}
