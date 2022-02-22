package kafka;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * <h1>堆外内存</h1>
 *
 * <p>https://github.com/snazy/ohc OHC 是在 2014/15 年为Apache Cassandra 2.2 和 3.0 开发的，用作 新的行缓存后端。
 *
 * <p>考虑使用缓存时，本地缓存是最快速的，但会给虚拟机带来GC压力。使用硬盘或者分布式缓存的响应时间会比较长，这时候「堆外缓存」会是一个比较好的选择。
 *
 * <p>ehcache 通用的缓存框架，支持堆内，堆外，磁盘等，多种模式
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-22 : base version.
 */
@Slf4j
public class DirectMemoryTest {

  public static void main(String[] args) throws InterruptedException {

    // 堆外 内存池 对象实例不在【堆内存】中
    final CacheConfiguration<String, OffHeapClass> configurationOff =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                OffHeapClass.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(1, MemoryUnit.MB).build())
            .build();
    // 堆内 内存，速度比 堆外快
    final CacheConfiguration<String, HeapClass> configurationIn =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                HeapClass.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1, MemoryUnit.MB).build())
            .build();
    // 创建内存
    final CacheManager offCacheManager =
        CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("offHeapCache", configurationOff)
            .build(true);
    Cache<String, OffHeapClass> offHeapCache =
        offCacheManager.getCache("offHeapCache", String.class, OffHeapClass.class);

    // 创建内存
    final CacheManager cacheManager =
        CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("heapCache", configurationIn)
            .build(true);
    Cache<String, HeapClass> heapCache =
        cacheManager.getCache("heapCache", String.class, HeapClass.class);

    for (int i = 0; i < 100; i++) {
      final String key = String.valueOf(i);
      offHeapCache.put(key, new OffHeapClass(key, "value" + key));
      heapCache.put(key, new HeapClass(key, "value" + key));
      TimeUnit.SECONDS.sleep(10);
    }
    log.info(offHeapCache.get("7").toString());
    offCacheManager.close();
    cacheManager.close();
  }
}

@Data
@AllArgsConstructor
class OffHeapClass implements Serializable {
  private String key;
  private String value;
}

@Data
@AllArgsConstructor
class HeapClass implements Serializable {
  private String key;
  private String value;
}
