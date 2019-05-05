package weather;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UtilTest {

    @Test
    public void shouldRoundUpToTwoDP() {
        assertThat(Util.round(3.7568),is(equalTo(3.76)));
    }
}
