package design;

import design.domain.StaticFactory;
import design.domain.chain.AbstractChainHandler;
import design.domain.chain.ChainHandlerA;
import design.domain.chain.ChainHandlerB;

/**
 * ChainMain.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
public class ChainMain {

  public static void main(String[] args) {
    final AbstractChainHandler handlerA =
        StaticFactory.creatAbstractChainHandler(ChainHandlerA.class);
    final AbstractChainHandler handlerB =
        StaticFactory.creatAbstractChainHandler(ChainHandlerB.class);
    handlerA.setNext(handlerB);
    handlerA.handler();
  }
}
