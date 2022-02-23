import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CollectionTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-10 : base version.
 */
public class CollectionTest {

  public static void main(String[] args) {
    queueTest();
    mapTest();
    listTest();
    setTest();
  }

  static void queueTest() {
    final String[] strings = new String[] {"同", "步", "方", "法", "和", "代", "码块"};
    final Queue<String> arrayDeque = new ArrayDeque<>();
    final Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
    final Queue<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    for (String string : strings) {
      arrayDeque.offer(string);
      arrayBlockingQueue.offer(string);
      concurrentLinkedDeque.offer(string);
    }
    System.out.println("arrayDeque = " + arrayDeque);
    System.out.println("arrayBlockingQueue = " + arrayBlockingQueue);
    System.out.println("concurrentLinkedDeque = " + concurrentLinkedDeque);
  }

  static void mapTest() {
    final String[] strings = new String[] {"同", "步", "方", "法", "和", "代", "码块"};
    final Map<Integer, String> hashMap = new HashMap<>(8);
    final Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
    final Map<Integer, String> hashtable = new Hashtable<>();
    final Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>(8);
    final Map<Integer, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

    for (int i = 0; i < strings.length; i++) {
      String string = strings[i];
      hashMap.put(i, string);
      linkedHashMap.put(i, string);
      hashtable.put(i, string);
      concurrentHashMap.put(i, string);
      concurrentSkipListMap.put(i, string);
    }
    System.out.println("hashMap = " + hashMap);
    System.out.println("linkedHashMap = " + linkedHashMap);
    System.out.println("hashtable = " + hashtable);
    System.out.println("concurrentHashMap = " + concurrentHashMap);
    System.out.println("concurrentSkipListMap = " + concurrentSkipListMap);
  }

  static void listTest() {
    final String[] strings = new String[] {"同", "步", "方", "法", "和", "代", "码块"};
    final List<String> arrayList = new ArrayList<>();
    final List<String> linkedList = new LinkedList<>();
    final List<String> vector = new Vector<>();
    final List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    for (String string : strings) {
      arrayList.add(string);
      linkedList.add(string);
      vector.add(string);
      copyOnWriteArrayList.add(string);
    }
    System.out.println("arrayList = " + arrayList);
    System.out.println("linkedList = " + linkedList);
    System.out.println("vector = " + vector);
    System.out.println("copyOnWriteArrayList = " + copyOnWriteArrayList);
  }

  static void setTest() {
    final String[] strings = new String[] {"同", "步", "方", "法", "和", "代", "码块"};
    final Set<String> linkedHashSet = new LinkedHashSet<>();
    final Set<String> hashSet = new HashSet<>();
    final Set<String> treeSet = new TreeSet<>();
    final Set<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
    for (String string : strings) {
      linkedHashSet.add(string);
      hashSet.add(string);
      treeSet.add(string);
      copyOnWriteArraySet.add(string);
    }
    System.out.println("linkedHashSet = " + linkedHashSet);
    System.out.println("hashSet = " + hashSet);
    System.out.println("treeSet = " + treeSet);
    System.out.println("copyOnWriteArraySet = " + copyOnWriteArraySet);
  }
}
