package design.domain.chain;

import lombok.extern.slf4j.Slf4j;

/**
 * ChainHandler.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public abstract class AbstractChainHandler {

  private AbstractChainHandler next;

  public void setNext(AbstractChainHandler next) {
    this.next = next;
  }

  /** 处理逻辑 */
  public abstract void handler();

  /** 调用下一个 */
  public void next() {
    if (next != null) {
      this.next.handler();
    }
  }
}
