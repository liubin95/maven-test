package domain.design.strategy;

/**
 * RewardContext. 策略模式，具体执行类
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
public class RewardContext {
  private final AbstractRewardStrategy strategy;

  public RewardContext(AbstractRewardStrategy strategy) {
    this.strategy = strategy;
  }

  public void doStrategy(long userId) {
    this.strategy.reward(userId);
    this.strategy.insertRewardAndSettlement(userId);
  }
}
