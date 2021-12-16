package domain.design.state;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadState. 状态1->操作1->状态2
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-11-19 : base version.
 */
@Slf4j
public abstract class AbstractThreadState {

  protected ThreadContext context;

  public AbstractThreadState(ThreadContext context) {
    this.context = context;
  }

  public void logState() {
    log.info(this.getClass().getSimpleName());
  }

  /** 启动 */
  abstract void start();

  /** 获取cpu */
  abstract void getCPU();

  /** stop */
  abstract void stop();

  void canNotDo(String name) {
    log.error("{} can not do {}", this.getClass().getSimpleName(), name);
  }
}

@Slf4j
class New extends AbstractThreadState {

  public New(ThreadContext context) {
    super(context);
  }

  @Override
  void start() {
    this.context.setState(new Runnable(this.context));
  }

  @Override
  void getCPU() {
    canNotDo("getCPU");
  }

  @Override
  void stop() {
    this.context.setState(new Dead(this.context));
  }
}

@Slf4j
class Runnable extends AbstractThreadState {

  public Runnable(ThreadContext context) {
    super(context);
  }

  @Override
  void start() {
    canNotDo("start");
  }

  @Override
  void getCPU() {
    this.context.setState(new Running(this.context));
  }

  @Override
  void stop() {
    this.context.setState(new Dead(this.context));
  }
}

@Slf4j
class Running extends AbstractThreadState {

  public Running(ThreadContext context) {
    super(context);
  }

  @Override
  void start() {
    canNotDo("start");
  }

  @Override
  void getCPU() {
    canNotDo("getCPU");
  }

  @Override
  void stop() {
    this.context.setState(new Dead(this.context));
  }
}

@Slf4j
class Dead extends AbstractThreadState {

  public Dead(ThreadContext context) {
    super(context);
  }

  @Override
  void start() {
    canNotDo("start");
  }

  @Override
  void getCPU() {
    canNotDo("getCPU");
  }

  @Override
  void stop() {
    canNotDo("stop");
  }
}
