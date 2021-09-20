package com.securithings.demo.interfaces;

public interface ObjectPool<T> {
	T get();
	void returnObject(T obj);
}
