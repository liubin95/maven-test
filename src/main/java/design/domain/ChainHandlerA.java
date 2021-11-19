package design.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * ChainHandlerA.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public class ChainHandlerA extends AbstractChainHandler {
  @Override
  public void handler() {
    log.info("ChainHandlerA handler");
  }
}
