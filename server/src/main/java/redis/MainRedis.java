package redis;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Main.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-09-28 : base version.
 */
public class MainRedis {

  public static void main(String[] args) {
    // 1. Create config object
    Config config = new Config();
    // use "rediss://" for SSL connection
    config
        .useClusterServers()
        .addNodeAddress("redis://172.19.0.3:7001")
        .addNodeAddress("redis://172.19.0.4:7002")
        .addNodeAddress("redis://172.19.0.2:7003");
    // Sync and Async API
    RedissonClient redisson = Redisson.create(config);

    final RSet<Integer> rset = redisson.getSet("Rset");
    System.out.println("rset.size" + rset.size());
    for (int i = 0; i < 10; i++) {
      rset.add(i);
      System.out.println("rset.add(" + i + ");");
    }
    System.out.println("rset data ok");
    final RSet<Integer> rSet2 = redisson.getSet("Rset");
    rSet2.forEach(System.out::println);
  }
}
