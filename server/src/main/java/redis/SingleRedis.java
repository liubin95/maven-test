package redis;

import org.redisson.Redisson;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j;

/**
 * SingleRedis.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-25 : base version.
 */
@Log4j
public class SingleRedis {

  private static final RedissonClient REDISSON;

  static {
    // 1. Create config object
    Config config = new Config();
    // use "rediss://" for SSL connection
    config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    // Sync and Async API
    REDISSON = Redisson.create(config);
  }

  public static void main(String[] args) {
    bucket();
  }

  static void bucket() {
    final RBucket<String> bucket = REDISSON.getBucket("single-redis-bucket");
    bucket.set("hello redis", 10L, TimeUnit.SECONDS);
    final long now = System.currentTimeMillis();
    bucket.addListener(
        (ExpiredObjectListener) s -> log.info((System.currentTimeMillis() - now) + " " + s));
  }
}
