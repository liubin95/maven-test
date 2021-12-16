package domain.design.chain;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * SubjectHandler.cglib 的代理模式
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public class SubjectHandler implements MethodInterceptor {

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    // 非目标方法
    if (!Objects.equals("handler", method.getName())) {
      return methodProxy.invokeSuper(o, objects);
    }
    log.info("SubjectHandler intercept {}#{}", method.getClass(), method.getName());
    // 执行逻辑
    final Object o1 = methodProxy.invokeSuper(o, objects);
    AbstractChainHandler handler = (AbstractChainHandler) o;
    // 调用下一个逻辑
    handler.next();
    return o1;
  }
}
