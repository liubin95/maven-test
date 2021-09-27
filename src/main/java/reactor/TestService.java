package reactor;

import com.google.common.util.concurrent.FutureCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TestService.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-09-23 : base version.
 */
public interface TestService {

  void getFavoritesCallBack(FutureCallback<List<String>> callback);

  void getDetailCallBack(String id, FutureCallback<String> callback);

  List<String> getFavoritesSync();

  String getDetailSync(String id);

  Flux<String> getFavoritesReactor();

  Mono<String> getDetailReactor(String id);

  Observable<String> getFavoritesRxJava();

  Single<String> getDetailRxJava(String id);
}
