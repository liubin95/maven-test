package guava;

import com.google.common.hash.Hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.extern.log4j.Log4j;

/**
 * ConsistencyHash. 一致性hash
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-11 : base version.
 */
@Log4j
public class ConsistencyHash {

  private static final List<Map<String, String>> MAPS =
      new ArrayList<Map<String, String>>() {
        {
          add(new HashMap<>());
          add(new HashMap<>());
          add(new HashMap<>());
        }
      };

  public static void main(String[] args) {
    final String[] strings = new String[] {"java", "python", "go", "java"};
    for (String string : strings) {
      final int i = Hashing.consistentHash(Objects.hashCode(string), MAPS.size());
      log.info(i);
      MAPS.get(i - 1).put(string, string);
    }
    System.out.println(MAPS.toString());
    // add
    log.info("------------");
    MAPS.add(new HashMap<>());
    MAPS.add(new HashMap<>());
    for (String string : strings) {
      final int i = Hashing.consistentHash(Objects.hashCode(string), MAPS.size());
      log.info(i);
    }
    // remove
    MAPS.remove(0);
    MAPS.remove(0);
    MAPS.remove(0);
    log.info("------------");
    for (String string : strings) {
      final int i = Hashing.consistentHash(Objects.hashCode(string), MAPS.size());
      log.info(i);
    }
  }
}
