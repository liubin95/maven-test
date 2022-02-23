import java.util.Arrays;

/**
 * MergeSort.归并排序
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-16 : base version.
 */
public class MergeSort {

  public static void main(String[] args) {
    final int[] mergeSort = mergeSort(new int[] {4, 5, 7, 8}, new int[] {1, 2, 3, 6});
    System.out.println(Arrays.toString(mergeSort));
  }

  static int[] mergeSort(int[] arr1, int[] arr2) {
    final int[] res = new int[arr1.length + arr2.length];
    // 左序列指针
    int left = 0;
    // 右序列指针
    int right = 0;
    // res数组指针
    int t = 0;
    while (left < arr1.length && right < arr2.length) {
      if (arr1[left] < arr2[right]) {
        res[t++] = arr1[left++];
      } else {
        res[t++] = arr2[right++];
      }
    }
    // 将左边剩余元素填充进temp中
    while (left < arr1.length) {
      res[t++] = arr1[left++];
    }
    // 将右序列剩余元素填充进temp中
    while (right < arr2.length) {
      res[t++] = arr2[right++];
    }
    return res;
  }
}
