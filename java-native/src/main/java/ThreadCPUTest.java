import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ThreadCPUTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-10 : base version.
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class ThreadCPUTest {

  private static final short THREAD_NUM = 6;
  private static final short SUM_NUM = 20;
  private static final int[] INTS = new int[10000 * 10000];

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 并行设置数组
    Arrays.parallelSetAll(INTS, (i) -> new Random().nextInt());

    long start = System.currentTimeMillis();
    System.out.println(sync());
    System.out.println("sync = " + (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    System.out.println(async());
    System.out.println("async = " + (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    // 数组并行求和，会改变原有数组
    Arrays.parallelPrefix(INTS, (left, right) -> 20 * left + 20 * right);
    System.out.println(INTS[INTS.length - 1]);
    System.out.println("parallelPrefix = " + (System.currentTimeMillis() - start));
  }

  static long sync() {
    long sum = 0L;
    for (int i = 0; i < SUM_NUM; i++) {
      for (int anInt : INTS) {
        sum += anInt;
      }
    }
    return sum;
  }

  static long async() throws InterruptedException, ExecutionException {
    long allSum = 0L;
    //noinspection unchecked
    final FutureTask<Long>[] futureTasks = new FutureTask[THREAD_NUM];
    for (int i = 0; i < futureTasks.length; i++) {
      final FutureTask<Long> futureTask = new FutureTask<>(new CPUCallable(i));
      futureTasks[i] = futureTask;
      //noinspection AlibabaAvoidManuallyCreateThread
      new Thread(futureTask).start();
    }
    for (FutureTask<Long> thread : futureTasks) {
      allSum += thread.get();
    }
    return allSum;
  }

  static class CPUCallable implements Callable<Long> {
    public int i;

    public CPUCallable(int i) {
      this.i = i;
    }

    @Override
    public Long call() {
      int sliceLength = INTS.length / THREAD_NUM;
      int[] slice = Arrays.copyOfRange(INTS, i * sliceLength, (i + 1) * sliceLength);
      long sum = 0L;
      for (int i = 0; i < SUM_NUM; i++) {
        for (int anInt : slice) {
          sum += anInt;
        }
      }
      return sum;
    }
  }
}
