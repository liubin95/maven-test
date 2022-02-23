import java.util.stream.IntStream;

/**
 * Singleton.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-15 : base version.
 */
public class Singleton {

  public static void main(String[] args) {
    IntStream.range(0, 10)
        .forEach(
            i -> {
              System.out.println(Load.getInstance());
              System.out.println(LazyLoad.getInstance());
            });
  }
}

class Load {
  private static final Load LOAD = new Load();

  private Load() {}

  public static Load getInstance() {
    return LOAD;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}

class LazyLoad {

  private static volatile LazyLoad load = null;

  private LazyLoad() {}

  public static LazyLoad getInstance() {
    if (load == null) {
      synchronized (LazyLoad.class) {
        if (load == null) {
          load = new LazyLoad();
        }
      }
    }
    return load;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
