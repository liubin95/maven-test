package domain.design;

import net.sf.cglib.proxy.Enhancer;

import domain.design.chain.AbstractChainHandler;
import domain.design.chain.SubjectHandler;
import domain.design.strategy.AbstractRewardStrategy;

/**
 * StrategyFactory. 策略的抽象工厂-->修改为静态工厂
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
public interface StaticFactory {

  /**
   * 创建策略
   *
   * @param c 类型
   * @return 策略
   */
  static <T extends AbstractRewardStrategy> AbstractRewardStrategy creatRewardStrategy(Class<T> c) {
    AbstractRewardStrategy instance = null;
    try {
      // 反射获取对象
      instance = (AbstractRewardStrategy) Class.forName(c.getName()).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return instance;
  }

  /**
   * 创建责任链
   *
   * @param c 类型
   * @param <T> 泛型
   * @return 对象
   */
  static <T extends AbstractChainHandler> AbstractChainHandler creatAbstractChainHandler(
      Class<T> c) {
    // 代理获取对象
    return (AbstractChainHandler) Enhancer.create(c, new SubjectHandler());
  }
}
