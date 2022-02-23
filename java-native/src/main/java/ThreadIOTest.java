import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

/**
 * ThreadTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-10 : base version.
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class ThreadIOTest {

  private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(3);

  public static void main(String[] args) throws IOException, InterruptedException {
    long start = System.currentTimeMillis();
    sync();
    System.out.println("sync = " + (System.currentTimeMillis() - start));
    start = System.currentTimeMillis();
    async();
    COUNT_DOWN_LATCH.await();
    System.out.println("async = " + (System.currentTimeMillis() - start));
  }

  static void sync() throws IOException {
    File file = new File("./input");
    File[] listFiles = file.listFiles();
    assert listFiles != null;
    for (File listFile : listFiles) {
      System.out.println(listFile.getName());
      transport(listFile);
    }
  }

  static void async() {
    File file = new File("./input");
    File[] listFiles = file.listFiles();
    assert listFiles != null;
    for (File listFile : listFiles) {
      System.out.println(listFile.getName());
      //noinspection AlibabaAvoidManuallyCreateThread
      new Thread(
              () -> {
                try {
                  transport(listFile);
                } catch (IOException e) {
                  e.printStackTrace();
                } finally {
                  // 倒数器减1
                  COUNT_DOWN_LATCH.countDown();
                }
              })
          .start();
    }
  }

  static void transport(File source) throws IOException {
    final File dest = new File("./output/" + source.getName());
    try (InputStream input = new FileInputStream(source);
        OutputStream output = new FileOutputStream(dest)) {

      byte[] buf = new byte[1024];
      int bytesRead;
      while ((bytesRead = input.read(buf)) > 0) {
        output.write(buf, 0, bytesRead);
      }
    }
  }
}
