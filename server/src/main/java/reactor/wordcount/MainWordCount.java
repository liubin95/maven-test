package reactor.wordcount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.math.MathFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * Main.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-09-24 : base version.
 */
public class MainWordCount {

  public static void main(String[] args) throws IOException {
    //    fileRead();
    //    fileReadReactor();
    final Stream<Path> walk = Files.walk(Paths.get("D:\\tmp"));
    walk.forEach(path -> System.out.println(path.toString()));
    System.out.println(MathFlux.sumInt(Flux.range(0, 10)).block());
  }

  static void fileRead() throws IOException {
    final Map<String, Long> collect =
        Files.lines(Paths.get("D:\\tmp\\input\\name.log"))
            .flatMap(s -> Stream.of(s.split(" ")))
            .collect(Collectors.toMap(k -> k, v -> 1L, Long::sum));
    System.out.println(collect);
  }

  static void fileReadReactor() {
    Flux.using(
            () -> Files.lines(Paths.get("D:\\tmp\\input\\name.log")),
            Flux::fromStream,
            BaseStream::close)
        .flatMap(s -> Flux.fromArray(s.split(" ")))
        .log("flatMap")
        .map(s -> Tuples.of(s, 1L))
        .log("map")
        .collect(Collectors.toMap(Tuple2::getT1, v -> 1L, Long::sum))
        .subscribe(System.out::println);
  }
}
