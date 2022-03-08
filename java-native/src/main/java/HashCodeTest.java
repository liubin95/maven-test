import java.util.Objects;

/**
 * HashCodeTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-03 : base version.
 */
public class HashCodeTest {

  public static void main(String[] args) {
    //
    final String s = "hello";
    System.out.println(s.hashCode());
    System.out.println(System.identityHashCode(s));
    System.out.println(Objects.hashCode(s));

    final ZhaoYiQingSB sb = new ZhaoYiQingSB(s);

    System.out.println(sb.hashCode());
    System.out.println(System.identityHashCode(sb));
    System.out.println(Objects.hashCode(sb));
  }
}

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
final class ZhaoYiQingSB {
  String name;

  public ZhaoYiQingSB(String name) {
    this.name = name;
  }
}
