import de.vandermeer.asciitable.AsciiTable;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

import lombok.extern.slf4j.Slf4j;

/**
 * LockTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-08 : base version.
 */
@Slf4j
public class LockTest {

  /** 线程池 */
  @SuppressWarnings("AlibabaThreadPoolCreation")
  private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(8);

  private static final int NUMBER_OF_TIMES = 10;
  private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(NUMBER_OF_TIMES);

  public static void main(String[] args) throws InterruptedException {
    final Random random = new Random();
    final AsciiTable table = new AsciiTable();
    table.addRule();
    table.addRow(
        "PointNoLock",
        "PointLock",
        "PointReadWriteLock",
        "PointStampedLock",
        "PointAtomicReference");
    table.addRule();

    final BasePoint[] basePoints =
        new BasePoint[] {
          new PointNoLock(),
          new PointLock(),
          new PointReadWriteLock(),
          new PointStampedLock(),
          new PointAtomicReference()
        };
    for (int i = 0; i < NUMBER_OF_TIMES; i++) {
      THREAD_POOL.execute(
          () -> {
            try {
              TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            final double x = random.nextDouble();
            final double y = random.nextDouble();
            for (final BasePoint basePoint : basePoints) {
              basePoint.move(x, y);
            }
            COUNT_DOWN_LATCH.countDown();
          });
    }
    COUNT_DOWN_LATCH.await();
    table.addRow(
        basePoints[0].distanceFromOrigin(),
        basePoints[1].distanceFromOrigin(),
        basePoints[2].distanceFromOrigin(),
        basePoints[3].distanceFromOrigin(),
        basePoints[4].distanceFromOrigin());
    table.addRule();

    String rend = table.render();
    System.out.println(rend);
    System.exit(0);
  }
}

abstract class BasePoint {
  /** 坐标点 */
  double x = 0.0, y = 0.0;
  /**
   * 移动
   *
   * @param x x
   * @param y y
   */
  abstract void move(double x, double y);
  /**
   * 获取到原点的距离
   *
   * @return 距离
   */
  abstract double distanceFromOrigin();
}

class PointNoLock extends BasePoint {

  @Override
  public void move(double x, double y) {
    this.x += x;
    this.y += y;
  }

  @Override
  public double distanceFromOrigin() {
    return Math.sqrt(x * x + y * y);
  }
}

class PointLock extends BasePoint {
  /** 锁 */
  private final Lock lock = new ReentrantLock();

  @Override
  void move(double x, double y) {
    lock.lock();
    try {
      this.x += x;
      this.y += y;
    } finally {
      lock.unlock();
    }
  }

  @Override
  double distanceFromOrigin() {
    lock.lock();
    try {
      return Math.sqrt(x * x + y * y);
    } finally {
      lock.unlock();
    }
  }
}

class PointReadWriteLock extends BasePoint {

  /** 读写锁 */
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  @Override
  void move(double x, double y) {
    readWriteLock.writeLock().lock();
    try {
      this.x += x;
      this.y += y;
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  double distanceFromOrigin() {
    readWriteLock.readLock().lock();
    try {
      return Math.sqrt(x * x + y * y);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }
}

class PointStampedLock extends BasePoint {
  /** 乐观读写锁 */
  private final StampedLock stampedLock = new StampedLock();

  @Override
  void move(double x, double y) {
    final long stamp = stampedLock.writeLock();
    try {
      this.x += x;
      this.y += y;
    } finally {
      stampedLock.unlockWrite(stamp);
    }
  }

  @Override
  double distanceFromOrigin() {
    // 乐观读
    final long readStamp = stampedLock.tryOptimisticRead();
    if (readStamp == 0) {
      throw new RuntimeException("readStamp is 0");
    }
    double currentX = this.x, currentY = this.y;
    // 校验
    if (!stampedLock.validate(readStamp)) {
      // 失败，获取读锁
      final long readLock = stampedLock.readLock();
      try {
        currentX = this.x;
        currentY = this.y;
      } finally {
        stampedLock.unlockRead(readLock);
      }
    }
    return Math.sqrt(currentX * currentX + currentY * currentY);
  }
}

class PointAtomicReference extends BasePoint {
  AtomicReference<Double> x = new AtomicReference<>(0.0), y = new AtomicReference<>(0.0);

  @Override
  void move(double x, double y) {
    this.x.getAndUpdate(it -> it += x);
    this.y.getAndUpdate(it -> it += y);
  }

  @Override
  double distanceFromOrigin() {
    double currentX = this.x.get();
    double currentY = this.y.get();
    return Math.sqrt(currentX * currentX + currentY * currentY);
  }
}
