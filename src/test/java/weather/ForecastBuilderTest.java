package weather;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ForecastBuilderTest {

    @Test
    public void propertiesShouldBeAssignedFromConsumer() {
        double TEMP = 1.0;
        ForecastBuilder builder = new ForecastBuilder().with( fb -> { fb.temperature = TEMP;});
        assertThat(builder.temperature,is(equalTo(TEMP)));
    }
}
