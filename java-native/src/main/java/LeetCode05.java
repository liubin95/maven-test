import lombok.val;

/**
 * LeetCode1346.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-17 : base version.
 */
public class LeetCode05 {

  static int anInt = 0;

  public static void main(String[] args) {
    val s =
        "iptmykvjanwiihepqhzupneckpzomgvzmyoybzfynybpfybngttozprjbupciuinpzryritfmyxyppxigitnemanreexcpwscvcwddnfjswgprabdggbgcillisyoskdodzlpbltefiz";
    System.out.println(longestPalindrome(s));

    System.out.println(anInt);
  }

  public static String longestPalindrome(String s) {
    final int length = s.length();
    if (length == 1) {
      return s;
    }
    if (length == 2 && !checkPalindrome(s)) {
      return s.substring(0, 1);
    }
    for (int i = length + 1; i >= 2; i--) {
      for (int j = 0; j < length + 1 - i; j++) {
        final String s1 = s.substring(j, j + i);
        anInt++;
        System.out.println(s1);
        if (checkPalindrome(s1)) {
          return s1;
        }
      }
    }
    return s.substring(0, 1);
  }

  public static boolean checkPalindrome(String s) {
    if (s.length() == 1) {
      return false;
    }
    final String s1;
    final String s2;
    if (s.length() % 2 == 0) {
      s1 = s.substring(0, (s.length() / 2));
      s2 = s.substring((s.length() / 2));
    } else {
      s1 = s.substring(0, ((s.length() - 1) / 2));
      s2 = s.substring(((s.length() + 1) / 2));
    }
    return s1.equals(new StringBuilder(s2).reverse().toString());
  }
}
