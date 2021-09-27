import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * GuavaTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-08-16 : base version.
 */
public class GuavaTest {

  public static void main(String[] args) {
    final BloomFilter<String> bloomFilter =
        BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 10);
    bloomFilter.put("qwe");
    bloomFilter.put("zxc");
    bloomFilter.put("asd");
    System.out.println(bloomFilter.mightContain("qwer"));
  }
}
