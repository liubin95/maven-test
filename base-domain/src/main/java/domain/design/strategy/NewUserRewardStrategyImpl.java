package domain.design.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * NewUserRewardStrategyImpl.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public class NewUserRewardStrategyImpl extends AbstractRewardStrategy {
  @Override
  public void reward(long userId) {
    log.info("new user reward {}", userId);
  }
}
