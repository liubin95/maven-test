import java.util.Date;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * DateMain.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-25 : base version.
 */
@Slf4j
public class DateMain {

  public static void main(String[] args) throws InterruptedException {
    while (true) {
      TimeUnit.SECONDS.sleep(5);
      //      System.out.println(new Date());
      log.info("log {}", new Date());
    }
  }
}
