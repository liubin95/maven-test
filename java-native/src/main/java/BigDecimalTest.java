import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * BigDecimalTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-30 : base version.
 */
public class BigDecimalTest {

  public static void main(String[] args) {
    // 取整数
    final BigDecimal decimal = new BigDecimal("123.123");
    final BigDecimal scale = decimal.setScale(2, RoundingMode.HALF_UP);
    System.out.println(decimal);
    System.out.println(scale);

    // 求和
    final Stream<BigDecimal> bigDecimalStream = Stream.of(null, null, null);
    final BigDecimal reduce =
        bigDecimalStream.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    System.out.println(reduce.equals(BigDecimal.ZERO) ? null : reduce);
  }
}
