import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;

/**
 * SynergeticProcess.
 *
 * <p>notify 和 wait 的使用方法，两个线程交替打印
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-07 : base version.
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
@Slf4j
public class SynergeticProcess {

  private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(10);
  private static int I = 0;

  public static void main(String[] args) throws InterruptedException {
    THREAD2.start();
    THREAD1.start();
    COUNT_DOWN_LATCH.await();
    System.exit(0);
  }

  private static final Thread THREAD1 =
      new Thread(
          new Runnable() {
            @Override
            public void run() {
              while (COUNT_DOWN_LATCH.getCount() > 0) {
                try {
                  log.info("{} {}", I++, Thread.currentThread().getName());
                  synchronized (THREAD2) {
                    THREAD2.notify();
                  }
                  synchronized (THREAD1) {
                    COUNT_DOWN_LATCH.countDown();
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
              while (COUNT_DOWN_LATCH.getCount() > 0) {
                try {
                  log.info("{} {}", I++, Thread.currentThread().getName());
                  synchronized (THREAD1) {
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
