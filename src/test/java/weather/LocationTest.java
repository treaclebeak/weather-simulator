package weather;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LocationTest {

    @Test
    public void shouldCreateLocationFromValidCSVLine() {
        String line = "\"608\";\"Australia\";\"Sydney\";\"-33.8678500\";\"151.2073212\";\"1.0\"";
        Location location = Location.fromCSVLine(line);
        assertThat(location.getName(),is(equalTo("Sydney")));
        assertThat(location.getLatitude(),is(equalTo(-33.8678500)));
        assertThat(location.getLongitude(),is(equalTo(151.2073212)));
        assertThat(location.getElevation(),is(equalTo(1.0)));

    }
}
