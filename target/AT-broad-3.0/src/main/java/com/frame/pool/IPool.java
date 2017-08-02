package com.frame.pool;

public interface IPool<T> {

	T borrow();
	
	void returnPool(T t);

	void shutdown();

	boolean isShutdown();
	
}
