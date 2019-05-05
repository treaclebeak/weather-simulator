package weather;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * Context holder for weather related variables
 */
class ForecastBuilder {

        double temperature;
        double humidity;
        LocalDateTime date;
        double pressure;
        String conditions;
        Location location;


        ForecastBuilder with(Consumer<ForecastBuilder> builderFunction) {
            builderFunction.accept(this);
            return this;
        }
    }


