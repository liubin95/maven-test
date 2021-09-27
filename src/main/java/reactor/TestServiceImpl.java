package reactor;

import com.google.common.util.concurrent.FutureCallback;

import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TestServiceImpl.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-09-23 : base version.
 */
public class TestServiceImpl implements TestService {

    private final TestDao testDao = new TestDao();

    @Override
    public void getFavoritesCallBack(FutureCallback<List<String>> callback) {
        callback.onSuccess(testDao.getFavorites());
    }

    @Override
    public void getDetailCallBack(String id, FutureCallback<String> callback) {
        if (id.contains("Exception")) {
            callback.onFailure(new RuntimeException("Exception 存在"));
        } else {
            callback.onSuccess(testDao.getDetail(id));
        }
    }

    @Override
    public List<String> getFavoritesSync() {
        if (new Random().nextBoolean()) {
            throw new RuntimeException("bad luck");
        }
        return testDao.getFavorites();
    }

    @Override
    public String getDetailSync(String id) {
        if (id.contains("Exception")) {
            throw new RuntimeException("Exception 存在");
        } else {
            return testDao.getDetail(id);
        }
    }

    @Override
    public Flux<String> getFavoritesReactor() {
        return Flux.fromIterable(testDao.getFavorites());
    }

    @Override
    public Mono<String> getDetailReactor(String id) {
        if (id.contains("Exception")) {
            return Mono.error(new RuntimeException("Exception 存在"));
        } else {
        return Mono.just(testDao.getDetail(id));
        }
    }

    @Override
    public Observable<String> getFavoritesRxJava() {
        return Observable.fromIterable(testDao.getFavorites());
    }

    @Override
    public Single<String> getDetailRxJava(String id) {
        if (id.contains("Exception")) {
            return Single.error(new RuntimeException("Exception 存在"));
        } else {
            return Single.just(testDao.getDetail(id));
        }
    }
}
