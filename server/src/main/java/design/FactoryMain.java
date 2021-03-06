package design;

import domain.design.StaticFactory;
import domain.design.strategy.AbstractRewardStrategy;
import domain.design.strategy.NewUserRewardStrategyImpl;
import domain.design.strategy.OldUserRewardStrategyImpl;
import domain.design.strategy.RewardContext;

/**
 * FactoryMain. 工厂模式和策略模式
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
public class FactoryMain {
  public static void main(String[] args) {
    final int[] userArr = new int[] {1, 2, 3, 4, 5, 6};
    for (int i : userArr) {
      // 策略
      AbstractRewardStrategy strategy;
      // 生产不同的策略
      if (i % 2 == 0) {
        strategy = StaticFactory.creatRewardStrategy(NewUserRewardStrategyImpl.class);
      } else {
        strategy = StaticFactory.creatRewardStrategy(OldUserRewardStrategyImpl.class);
      }
      // 执行策略
      new RewardContext(strategy).doStrategy(i);
    }
  }
}
