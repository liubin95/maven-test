package design;

import domain.design.state.ThreadContext;

/**
 * StateMain.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
public class StateMain {

  public static void main(String[] args) {
    // 状态模式
    final ThreadContext threadContext = new ThreadContext();
    threadContext.getCPU();
    threadContext.start();
    threadContext.getCPU();
    threadContext.stop();
  }
}
