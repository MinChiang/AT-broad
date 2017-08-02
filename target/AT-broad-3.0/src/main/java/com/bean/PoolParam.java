package com.bean;

public class PoolParam {
	private int maxSize;
	private int initSize;
	private Class<?> cls;
	private Class<?>[] constructorParamCls;
	private Object[] constructorParam;

	public PoolParam() {
		super();
	}

	public PoolParam(int maxSize, int initSize, Class<?> cls, Class<?>[] constructorParamCls,
			Object[] constructorParam) {
		super();
		this.maxSize = maxSize;
		this.initSize = initSize;
		this.cls = cls;
		this.constructorParamCls = constructorParamCls;
		this.constructorParam = constructorParam;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getInitSize() {
		return initSize;
	}

	public void setInitSize(int initSize) {
		this.initSize = initSize;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public Class<?>[] getConstructorParamCls() {
		return constructorParamCls;
	}

	public void setConstructorParamCls(Class<?>[] constructorParamCls) {
		this.constructorParamCls = constructorParamCls;
	}

	public Object[] getConstructorParam() {
		return constructorParam;
	}

	public void setConstructorParam(Object[] constructorParam) {
		this.constructorParam = constructorParam;
	}

}
