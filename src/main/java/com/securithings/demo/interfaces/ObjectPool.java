package com.securithings.demo.interfaces;

import java.util.concurrent.TimeoutException;

public interface ObjectPool<T> {
    T get() throws InterruptedException, TimeoutException;
    
    void returnObject(T obj);
}
