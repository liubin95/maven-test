import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Main0719.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-07-19 : base version.
 */
public class BuildTree {

  public static void main(String[] args) {
    List<Node> nodeList =
        new ArrayList<Node>() {
          {
            add(new Node(1, null));
            add(new Node(2, 1));
            add(new Node(3, 1));
            add(new Node(4, 2));
            add(new Node(5, 3));
            add(new Node(6, 5));
          }
        };
    Map<Integer, Node> temp = nodeList.stream().collect(Collectors.toMap(i -> i.id, i -> i));
    for (Node value : temp.values()) {
      if (temp.containsKey(value.pid)) {
        final Node parent = temp.get(value.pid);
        parent.children.add(value);
      }
    }
    final Optional<Node> first = temp.values().stream().filter(i -> i.pid == null).findFirst();
    System.out.println(first.orElse(null));
  }

  static class Node {

    Integer id;
    List<Node> children;
    Integer pid;

    public Node(Integer id, Integer pid) {
      this.id = id;
      this.pid = pid;
      children = new LinkedList<>();
    }

    @Override
    public String toString() {

      return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
          .add("id=" + id)
          .add("children=" + children)
          .add("pid=" + pid)
          .toString();
    }
  }
}
