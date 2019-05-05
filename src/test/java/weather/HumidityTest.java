package weather;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class HumidityTest {

    @Test
    public void relativeHumidityShouldNotExceed100Percent() {
        assertThat(HumidityGauge.calculate(25.0),is(lessThan(100.0)));
    }
}
