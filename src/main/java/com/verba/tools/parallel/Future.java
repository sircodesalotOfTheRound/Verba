package com.verba.tools.parallel;

import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class Future<T> {
  private final Supplier<T> supplierFunction;
  boolean isComplete;
  private boolean hasStarted;
  private T result;

  private Future(Supplier<T> supplierFunction) {
    this.supplierFunction = supplierFunction;
    this.isComplete = false;
    this.hasStarted = false;

    this.start();
  }

  private void start() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        Future.this.set();
      }
    };

    thread.start();
  }

  public static <T> Future<T> promise(Supplier<T> supplierFunction) {
    return new Future<>(supplierFunction);
  }

  private void set() {
    // Lock access
    synchronized (this.supplierFunction) {
      this.hasStarted = true;
      this.result = this.supplierFunction.get();
    }

    this.isComplete = true;
  }

  public boolean isComplete() {
    return this.isComplete;
  }

  public T get() {
    while (!this.hasStarted) ; // Spin until the thread has at least started
    synchronized (this.supplierFunction) {
      return this.result;
    }
  }
}
