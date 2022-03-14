import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * SynergeticProcess.
 *
 * <p>协程，多个线程之间协同工作
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-07 : base version.
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
@Slf4j
public class SynergeticProcess {

  private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(10);
  private static final Semaphore SEMAPHORE = new Semaphore(1, true);
  private static int I = 0;

  public static void main(String[] args) throws InterruptedException {
    waitAndNotify();
    withSemaphore();
    withCyclicBarrier();
    COUNT_DOWN_LATCH.await();
    System.exit(0);
  }

  /**
   * 使用 wait 和 Notify 方法，交替打印数字
   *
   * @throws InterruptedException Exception
   */
  private static void waitAndNotify() throws InterruptedException {
    log.debug("THREAD1 {}", THREAD1.getState());
    THREAD1.start();
    THREAD2.start();
    COUNT_DOWN_LATCH.await();
    log.debug("THREAD1 {}", THREAD1.getState());
  }

  /** 使用Semaphore 信号量控制，两个线程交替打印 */
  private static void withSemaphore() {
    new Thread(new RunnableWithSemaphore()).start();
    new Thread(new RunnableWithSemaphore()).start();
  }

  /** */
  private static void withCyclicBarrier() {
    final int[] ints = new int[10000 * 10000];
    final int threadNum = 5;
    final int[] res = new int[threadNum];
    // 并行设置数组
    Arrays.parallelSetAll(ints, (i) -> new Random().nextInt());
    log.info("sum stream:{}", Arrays.stream(ints).sum());
    int sliceLength = ints.length / threadNum;
    // 线程屏障
    final CyclicBarrier barrier =
        new CyclicBarrier(
            threadNum,
            // 所有线程到达屏障
            () -> {
              final int sum = Arrays.stream(res).sum();
              log.info("sum:{}", sum);
            });
    for (int i = 0; i < threadNum; i++) {
      int[] slice = Arrays.copyOfRange(ints, i * sliceLength, (i + 1) * sliceLength);
      int finalI = i;
      new Thread(
              () -> {
                res[finalI] = Arrays.stream(slice).sum();
                try {
                  // 单个线程完成
                  barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                  e.printStackTrace();
                }
              })
          .start();
    }
  }

  static class RunnableWithSemaphore implements Runnable {

    @Override
    public void run() {
      //noinspection InfiniteLoopStatement
      while (true) {
        try {
          SEMAPHORE.acquire();
          log.info("{} {}", I++, Thread.currentThread().getName());
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          COUNT_DOWN_LATCH.countDown();
          SEMAPHORE.release();
        }
      }
    }
  }

  private static final Thread THREAD1 =
      new Thread(
          new Runnable() {
            @Override
            public void run() {
              while (true) {
                try {
                  log.info("{} {}", I++, Thread.currentThread().getName());
                  synchronized (THREAD2) {
                    THREAD2.notify();
                  }
                  synchronized (THREAD1) {
                    COUNT_DOWN_LATCH.countDown();
                    log.debug("THREAD1 {}", THREAD1.getState());
                    THREAD1.wait();
                  }
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            }
          },
          "THREAD1");

  private static final Thread THREAD2 =
      new Thread(
          new Runnable() {
            @Override
            public void run() {
              while (true) {
                try {
                  log.info("{} {}", I++, Thread.currentThread().getName());
                  synchronized (THREAD1) {
                    log.debug("THREAD1 {}", THREAD1.getState());
                    THREAD1.notify();
                  }
                  synchronized (THREAD2) {
                    COUNT_DOWN_LATCH.countDown();
                    THREAD2.wait();
                  }
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            }
          },
          "THREAD2");
}
