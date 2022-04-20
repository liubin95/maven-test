import java.util.Optional;

/**
 * OptionalTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-04-02 : base version.
 */
public class OptionalTest {

  public static void main(String[] args) {
    //    final Optional<Object> o = Optional.of(null);
    final Optional<Object> oo = Optional.ofNullable(null);
    final Optional<Object> om = Optional.empty();

    System.out.println(oo);
    System.out.println(om);
  }
}
