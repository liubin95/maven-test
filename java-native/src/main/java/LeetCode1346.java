import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode1346.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-17 : base version.
 */
public class LeetCode1346 {

  public static void main(String[] args) {
    System.out.println(checkIfExist(new int[] {10, 2, 5, 3}));
    System.out.println(checkIfExist(new int[] {7, 1, 14, 11}));
    System.out.println(checkIfExist(new int[] {3, 1, 7, 11}));

    System.out.println(checkIfExistHash(new int[] {10, 2, 5, 3}));
    System.out.println(checkIfExistHash(new int[] {7, 1, 14, 11}));
    System.out.println(checkIfExistHash(new int[] {3, 1, 7, 11}));
  }

  static boolean checkIfExist(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[i] == 2 * arr[j] || arr[j] == 2 * arr[i]) {
          return true;
        }
      }
    }
    return false;
  }

  static boolean checkIfExistHash(int[] arr) {
    Map<Integer, Integer> map = new HashMap<>(arr.length);
    for (int i = 0; i < arr.length; i++) {
      // 存在两倍
      if (map.containsKey(2 * arr[i]) && map.get(2 * arr[i]) != i) {
        return true;
      }
      // 存在1/2
      // 都是int，所以必然是偶数
      if (arr[i] % 2 == 0 && map.containsKey(arr[i] / 2) && map.get(arr[i] / 2) != i) {
        return true;
      }
      map.put(arr[i], i);
    }
    return false;
  }
}
