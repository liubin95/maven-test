import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-15 : base version.
 */
public class StreamTest {

  private static final Predicate<ReduceTest> EVEN_FUNCTIONAL = it -> it.getNum() % 2 == 0;

  public static void main(String[] args) {
    final Supplier<Stream<ReduceTest>> stream =
        () -> Stream.of(new ReduceTest(1), new ReduceTest(2), new ReduceTest(3));
    // 过滤
    final long count = stream.get().filter(EVEN_FUNCTIONAL).count();
    // 聚合
    final ReduceTest reduce =
        stream
            .get()
            .reduce(new ReduceTest(0), (o1, o2) -> new ReduceTest(o1.getNum() + o2.getNum()));
    // 最小值
    final Optional<ReduceTest> min = stream.get().min(Comparator.comparingInt(ReduceTest::getNum));
    // 统计数字
    final IntSummaryStatistics statistics =
        stream.get().mapToInt(ReduceTest::getNum).summaryStatistics();
    // 收集到其他类型集合
    final Set<ReduceTest> hashSet = stream.get().collect(Collectors.toCollection(HashSet::new));
    // 分区
    final Map<Boolean, List<ReduceTest>> partitioningBy =
        stream.get().collect(Collectors.partitioningBy(EVEN_FUNCTIONAL));
    // 将结果转化成一个值 平均值
    final Double averagingInt = stream.get().collect(Collectors.averagingInt(ReduceTest::getNum));
    // 分组
    final Map<Integer, List<ReduceTest>> groupBy =
        stream.get().collect(Collectors.groupingBy(ReduceTest::getNum));
    // 拼接字符串
    final String joiningString =
        stream
            .get()
            .map(it -> String.valueOf(it.getNum()))
            .collect(Collectors.joining(",", "[", "]"));
    // 组合收集器，下游收集器
    final Map<Boolean, Long> composeCollect =
        stream
            .get()
            .collect(Collectors.groupingBy(it -> it.getNum() % 2 == 0, Collectors.counting()));

    System.out.println(count);
    System.out.println(reduce);
    System.out.println(min.orElseThrow(RuntimeException::new));
    System.out.println(statistics);
    System.out.println(hashSet);
    System.out.println(partitioningBy);
    System.out.println(averagingInt);
    System.out.println(groupBy);
    System.out.println(joiningString);
    System.out.println(composeCollect);
  }
}

class ReduceTest {
  private int num;

  public ReduceTest(int num) {
    this.setNum(num);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ReduceTest.class.getSimpleName() + "[", "]")
        .add("num=" + getNum())
        .toString();
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReduceTest that = (ReduceTest) o;
    return num == that.num;
  }

  @Override
  public int hashCode() {
    return Objects.hash(num);
  }
}
