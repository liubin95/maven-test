import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 原子操作方式集合.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-08 : base version.
 */
@Slf4j
public class AtomicTest {

  private final int numberOfTimes = 100;
  /** 线程池 */
  @SuppressWarnings("AlibabaThreadPoolCreation")
  private final ExecutorService threadPool = Executors.newFixedThreadPool(8);
  /** 锁 */
  private final Lock lock = new ReentrantLock();

  private final AtomicInteger atomicInteger = new AtomicInteger(0);
  private final LongAdder longAdder = new LongAdder();
  private final AtomicReference<Integer> integerAtomicReference = new AtomicReference<>(0);
  /** 计数器 */
  private CountDownLatch countDownLatch = new CountDownLatch(numberOfTimes);

  private int numNotSync = 0;
  private volatile int numVolatile = 0;
  private int numSync = 0;
  private int numLock = 0;

  public AtomicTest() {}

  public static void main(String[] args) throws InterruptedException {
    final AtomicTest atomicTest = new AtomicTest();

    atomicTest.runNumNotSync();
    atomicTest.countDownLatch.await();
    atomicTest.reSetCountDownLatch();
    log.info("numNotSync {}", atomicTest.numNotSync);
    log.info("numVolatile {}", atomicTest.numVolatile);

    atomicTest.runNumSync();
    atomicTest.countDownLatch.await();
    atomicTest.reSetCountDownLatch();
    log.info("numSync {}", atomicTest.numSync);

    atomicTest.runLock();
    atomicTest.countDownLatch.await();
    atomicTest.reSetCountDownLatch();
    log.info("numLock {}", atomicTest.numLock);

    atomicTest.runAtomicInteger();
    atomicTest.countDownLatch.await();
    atomicTest.reSetCountDownLatch();
    log.info("atomicInteger {}", atomicTest.atomicInteger.get());

    atomicTest.runLongAdder();
    atomicTest.countDownLatch.await();
    atomicTest.reSetCountDownLatch();
    log.info("longAdder {}", atomicTest.longAdder.longValue());

    atomicTest.runAtomicReference();
    atomicTest.countDownLatch.await();
    atomicTest.reSetCountDownLatch();
    log.info("atomicReference {}", atomicTest.integerAtomicReference.get());

    System.exit(0);
  }

  /** 重新设置计数器 */
  private void reSetCountDownLatch() {
    this.countDownLatch = new CountDownLatch(numberOfTimes);
  }

  /**
   * 直接累加
   *
   * <p>volatile 修饰的累加
   */
  private void runNumNotSync() {
    for (int i = 0; i < numberOfTimes; i++) {
      threadPool.execute(
          () -> {
            try {
              TimeUnit.MILLISECONDS.sleep(10);
              numNotSync++;
              //noinspection NonAtomicOperationOnVolatileField
              numVolatile++;
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              countDownLatch.countDown();
            }
          });
    }
  }

  /** synchronized 同步代码块的累加 */
  private void runNumSync() {
    for (int i = 0; i < numberOfTimes; i++) {
      threadPool.execute(
          () -> {
            try {
              // todo 优化为锁对象
              synchronized (this) {
                TimeUnit.MILLISECONDS.sleep(10);
                numSync++;
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              countDownLatch.countDown();
            }
          });
    }
  }

  /** 锁 同步代码块 累加 */
  private void runLock() {
    for (int i = 0; i < numberOfTimes; i++) {
      threadPool.execute(
          () -> {
            lock.lock();
            try {
              TimeUnit.MILLISECONDS.sleep(10);
              numLock++;
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              lock.unlock();
              countDownLatch.countDown();
            }
          });
    }
  }

  /** AtomicInteger 原子累加器 */
  private void runAtomicInteger() {
    for (int i = 0; i < numberOfTimes; i++) {
      threadPool.execute(
          () -> {
            try {
              TimeUnit.MILLISECONDS.sleep(10);
              atomicInteger.addAndGet(1);
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              countDownLatch.countDown();
            }
          });
    }
  }

  /** LongAdder 原子累加器 加强版本 */
  private void runLongAdder() {
    for (int i = 0; i < numberOfTimes; i++) {
      threadPool.execute(
          () -> {
            try {
              TimeUnit.MILLISECONDS.sleep(10);
              longAdder.add(1L);
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              countDownLatch.countDown();
            }
          });
    }
  }

  /** integerAtomicReference 包装的cas类 */
  private void runAtomicReference() {
    for (int i = 0; i < numberOfTimes; i++) {
      threadPool.execute(
          () -> {
            try {
              TimeUnit.MILLISECONDS.sleep(10);
              integerAtomicReference.getAndUpdate(integer -> ++integer);
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              countDownLatch.countDown();
            }
          });
    }
  }
}
