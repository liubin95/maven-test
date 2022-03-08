import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

  @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
  public static void main(String[] args) {
    final Lock lock = new ReentrantLock(true);
    log.info(lock.toString());
    new Thread(
            () -> {
              while (true) {
                try {
                  TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                if (lock.tryLock()) {
                  log.info("{} lock success", Thread.currentThread().getName());
                  lock.unlock();
                }
              }
            })
        .start();
    new Thread(
            () -> {
              while (true) {
                try {
                  TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                lock.lock();
                try {
                  log.info("{} lock success", Thread.currentThread().getName());
                } finally {
                  lock.unlock();
                }
              }
            })
        .start();
  }
}
