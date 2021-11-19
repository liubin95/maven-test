package design.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * RewardStrategy. 抽象的策略
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public abstract class AbstractRewardStrategy {
  /**
   * 方法，对不同用户，执行不同结果
   *
   * @param userId userId
   */
  public abstract void reward(long userId);

  /**
   * 方法2，不同用户，相同的逻辑
   *
   * @param userId userId
   */
  public void insertRewardAndSettlement(long userId) {
    log.info("user insertRewardAndSettlement {}", userId);
  }
}
