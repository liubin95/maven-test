import org.slf4j.MDC;

import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadLocalTest.
 *
 * <h2>线程私有变量，不共享
 *
 * <p>用途：{@link MDC}等
 *
 * <p>映射的诊断上下文在客户端服务器架构中最为耀眼。 通常，多个客户端将由服务器上的多个线程提供服务。尽管
 * MDC类中的方法是静态的，但诊断上下文是按线程管理的，允许每个服务器线程带有不同的MDC标记。MDC诸如put()和get()仅影响 当前线程的
 * 和当前线程的子级的MDC操作。其他线程不受影响。鉴于信息是在每个线程的基础上管理的，每个线程都有自己的副本MDCMDCMDC. 因此，开发人员在使用
 * 编程时无需担心线程安全或同步，MDC因为它可以安全透明地处理这些问题。
 *
 * <p><a href="https://logback.qos.ch/manual/mdc.html">logback的MDC</a>
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-03-11 : base version.
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
@Slf4j
public class ThreadLocalTest {

  public static void main(String[] args) {
    final SharedMapWithUserContext user1 = new SharedMapWithUserContext(1);
    final Thread thread1 = new Thread(user1, "user1");
    log.info(user1.toString());
    final SharedMapWithUserContext user2 = new SharedMapWithUserContext(2);
    final Thread thread2 = new Thread(user2, "user2");
    log.info(user2.toString());
    thread2.start();
    thread1.start();
  }
}

class UserContext {
  private final String userName;

  public UserContext(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", UserContext.class.getSimpleName() + "[", "]")
        .add("userName='" + userName + "'")
        .toString();
  }
}

@Slf4j
class SharedMapWithUserContext implements Runnable {

  /** ThreadLocal 变量，线程之间隔离。需要在run方法设置 */
  private static final ThreadLocal<UserContext> CONTEXT_SAFE = new ThreadLocal<>();
  /** 普通变量 */
  private static UserContext context;

  private final Integer userId;

  public SharedMapWithUserContext(Integer userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SharedMapWithUserContext.class.getSimpleName() + "[", "]")
        .add("context='" + context + "'")
        .add("CONTEXT_SAFE='" + CONTEXT_SAFE.get() + "'")
        .toString();
  }

  @Override
  public void run() {
    UserRepository repository = new UserRepository();
    final UserContext context = new UserContext(repository.getUserName(userId));
    SharedMapWithUserContext.context = context;
    CONTEXT_SAFE.set(context);
    // sf4j的 MDC（映射上下文）
    MDC.put("context", context.getUserName());
    log.info(Thread.currentThread().getName() + " context " + context);
    log.info(Thread.currentThread().getName() + " CONTEXT_SAFE " + CONTEXT_SAFE.get());
    log.info(Thread.currentThread().getName() + " MDC " + MDC.get("context"));
    try {
      userDeal1();
      userDeal2();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      // ThreadLocal使用完需要清理
      CONTEXT_SAFE.remove();
      MDC.clear();
    }
  }

  private void userDeal1() throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(20);
    log.info(Thread.currentThread().getName() + " context " + context);
    log.info(Thread.currentThread().getName() + " CONTEXT_SAFE " + CONTEXT_SAFE.get());
    log.info(Thread.currentThread().getName() + " MDC " + MDC.get("context"));
    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(50));
    userDeal2();
  }

  private void userDeal2() throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(20);
    log.info(Thread.currentThread().getName() + " context " + context);
    log.info(Thread.currentThread().getName() + " CONTEXT_SAFE " + CONTEXT_SAFE.get());
    log.info(Thread.currentThread().getName() + " MDC " + MDC.get("context"));
  }
}

class UserRepository {
  String getUserName(Integer id) {
    return "userName from UserRepository " + id;
  }
}
