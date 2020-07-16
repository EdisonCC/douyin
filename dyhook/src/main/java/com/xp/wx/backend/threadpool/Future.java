package com.xp.wx.backend.threadpool;

public interface Future<T> {
    void cancel();

    T get();

    boolean isCancelled();

    boolean isDone();

    void waitDone();
}
