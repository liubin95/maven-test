import com.google.common.collect.Lists;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * SplitList.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-04-20 : base version.
 */
public class SplitList {

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    final int anInt = new Random().nextInt(200);
    for (int i = 0; i < anInt; i++) {
      list.add(i);
    }

    List<List<Integer>> result = splitListApache(list, 100);
    result.add(new ArrayList<>());
    System.out.printf(
        "anInt[%s] size[%s] , last[%s] , result[%s]",
        anInt,
        result.size(),
        result.get(result.size() - 1).size(),
        anInt == 100 * (result.size() - 1) + result.get(result.size() - 1).size());
  }

  public static <T> List<List<T>> splitListApache(List<T> list, int size) {
    return ListUtils.partition(list, size);
  }

  public static <T> List<List<T>> splitListGuava(List<T> list, int size) {
    return Lists.partition(list, size);
  }

  public static <T> List<List<T>> splitList(List<T> list, int size) {
    List<List<T>> result = new ArrayList<>();
    int listSize = list.size();
    int count = listSize / size;
    int lastCount = listSize % size;
    int fromIndex = 0;
    for (int i = 0; i < count; i++) {
      List<T> subList = list.subList(fromIndex, fromIndex + size);
      result.add(subList);
      fromIndex += size;
    }
    if (lastCount > 0) {
      List<T> subList = list.subList(fromIndex, fromIndex + lastCount);
      result.add(subList);
    }
    return result;
  }
}
