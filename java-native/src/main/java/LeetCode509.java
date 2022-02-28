/**
 * LeetCode1346.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-17 : base version.
 */
public class LeetCode509 {

  public static void main(String[] args) {
    System.out.println("fib= " + fib(5));
    System.out.println("fib= " + fibDP(5));
    System.out.println("tribonacci = " + tribonacci(4));
  }

  public static int fib(int n) {
    if (n <= 1) {
      return n;
    }
    return fib(n - 1) + fib(n - 2);
  }

  public static int fibDP(int n) {
    if (n <= 1) {
      return n;
    }
    final int[] ints = new int[n + 1];
    ints[0] = 0;
    ints[1] = 1;
    for (int i = 2; i <= ints.length - 1; i++) {
      ints[i] = ints[i - 1] + ints[i - 2];
    }

    return ints[n];
  }

  public static int tribonacci(int n) {
    if (n <= 1) {
      return n;
    }
    final int[] ints = new int[n + 1];
    ints[0] = 0;
    ints[1] = 1;
    ints[2] = 1;
    for (int i = 3; i <= ints.length - 1; i++) {
      ints[i] = ints[i - 1] + ints[i - 2] + ints[i - 3];
    }

    return ints[n];
  }
}
