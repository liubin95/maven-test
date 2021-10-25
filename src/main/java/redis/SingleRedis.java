package redis;

import org.redisson.Redisson;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * SingleRedis.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-25 : base version.
 */
public class SingleRedis {

  public static void main(String[] args) throws InterruptedException {
    // 1. Create config object
    Config config = new Config();
    // use "rediss://" for SSL connection
    config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    // Sync and Async API
    RedissonClient redisson = Redisson.create(config);
    final RBucket<String> bucket = redisson.getBucket("single-redis-bucket");
    bucket.set("hello redis", 10L, TimeUnit.SECONDS);
    final long now = System.currentTimeMillis();
    System.out.println(now);
    bucket.addListener(
        (ExpiredObjectListener)
            s -> System.out.println((System.currentTimeMillis() - now) + " " + s));
    TimeUnit.MINUTES.sleep(1);
  }
}
