package com.frame.pool;

public class DefaultObjectValidator<T> implements IObjectValidator<T> {

	@Override
	public boolean validate(T t) {
		return true;
	}

}
