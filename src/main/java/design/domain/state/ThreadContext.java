package design.domain.state;

/**
 * ThreadContext. 状态的上下文，暴露操作接口
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
public class ThreadContext {

  private AbstractThreadState state;

  public ThreadContext() {
    this.state = new New(this);
  }

  public void logState() {
    this.state.logState();
  }

  public AbstractThreadState getState() {
    return state;
  }

  void setState(AbstractThreadState state) {
    this.state = state;
  }

  public void start() {
    this.state.start();
    this.logState();
  }

  public void getCPU() {
    this.state.getCPU();
    this.logState();
  }

  public void stop() {
    this.state.stop();
    this.logState();
  }
}
