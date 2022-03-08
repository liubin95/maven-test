import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-07 : base version.
 */
public class AtomicTest {

  public static void main(String[] args) {
    // cas 实现
    final AtomicLong atomicLong = new AtomicLong(5L);
    System.out.println(atomicLong.addAndGet(5));
    System.out.println(atomicLong.accumulateAndGet(10, (left, right) -> left - right));

    // cas 实现 时间戳版本，避免ABA
    final int oldTime = 1;
    AtomicStampedReference<Long> longAtomicStampedReference =
        new AtomicStampedReference<>(5L, oldTime);
    longAtomicStampedReference.compareAndSet(5L, 10L, oldTime, 2);
    System.out.println(longAtomicStampedReference.getReference());

    // 性能优化 累加器
    final LongAdder longAdder = new LongAdder();
    longAdder.add(10L);
    System.out.println(longAdder.longValue());

    // 自定义累加规则的累加器
    final LongAccumulator longAccumulator = new LongAccumulator((left, right) -> left - right, 5);
    longAccumulator.accumulate(10);
    System.out.println(longAccumulator.get());
  }
}
