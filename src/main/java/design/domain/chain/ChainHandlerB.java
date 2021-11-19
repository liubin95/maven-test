package design.domain.chain;

import org.apache.pulsar.shade.org.apache.commons.lang3.RandomUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ChainHandlerB.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public class ChainHandlerB extends AbstractChainHandler {
  @Override
  public void handler() {
    if (RandomUtils.nextBoolean()) {
      throw new RuntimeException("bad luck");
    }
    log.info("ChainHandlerB handler");
  }
}
