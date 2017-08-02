package com.frame.pool;

public interface IObjectFactory<T> {

	T createObject();

	void releaseResource(T t);
}
