package reactor;

import com.google.common.util.concurrent.FutureCallback;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import reactor.core.scheduler.Schedulers;

/**
 * reactor.Reactor.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-09-23 : base version.
 */
public class Reactor {

    private static final TestService SERVICE = new TestServiceImpl();

    public static void main(String[] args) {
//        sync();
//        callBack();
        reactor();
//        rxJava();
    }

    static void rxJava() {
        SERVICE.getFavoritesRxJava()
                .map(SERVICE::getDetailRxJava)
                .map(Single::blockingGet)
                .subscribe(System.out::println, throwable -> System.err.println(throwable.getMessage()));

    }

    static void reactor() {
        SERVICE.getFavoritesReactor()
                .flatMap(SERVICE::getDetailReactor)
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(x -> System.out.println(Thread.currentThread().getName() + "\t" + x), throwable -> System.err.println(throwable.getMessage()));
    }

    static void callBack() {
        SERVICE.getFavoritesCallBack(new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(@Nullable List<String> strings) {
                assert strings != null;
                for (String s : strings) {
                    SERVICE.getDetailCallBack(s, new FutureCallback<String>() {
                        @Override
                        public void onSuccess(@Nullable String string) {
                            System.out.println(string);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            System.err.println(throwable.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.err.println(throwable.getMessage());
            }
        });
    }

    static void sync() {
        final List<String> favoritesSync = SERVICE.getFavoritesSync();
        for (String s : favoritesSync) {
            try {
                System.out.println(SERVICE.getDetailSync(s));
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }
        }
    }
}
