package weather;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
