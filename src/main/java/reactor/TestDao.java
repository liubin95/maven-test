package reactor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TestDao.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-09-23 : base version.
 */
public class TestDao {

    List<String> getFavorites() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList("Arrays", "RuntimeException", "TestService", "TestDao", "TimeUnit", "InterruptedException");
    }

    public String getDetail(String id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id + "getDetail";
    }
}
