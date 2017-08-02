package com.frame.invokerhandler;

import java.util.ArrayList;
import java.util.List;

import com.frame.invoker.IInvoker;

/**
 * @author MinChiang
 *
 * @date 2017年3月5日
 * 
 *
 */
public abstract class AbstractInvokerHandler implements IInvokerHandler {

	protected String invokerHandlerName;
	protected List<IInvoker> invokers;

	public AbstractInvokerHandler() {
		this.invokers = new ArrayList<IInvoker>();
	}

	public AbstractInvokerHandler(ArrayList<IInvoker> invokers) {
		super();
		this.invokers = invokers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invokerhandler.IInvokerHandler#getInvokerHandlerName()
	 */
	@Override
	public String getInvokerHandlerName() {
		return invokerHandlerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frame.invokerhandler.IInvokerHandler#setInvokerHandlerName(java.lang.
	 * String)
	 */
	@Override
	public void setInvokerHandlerName(String invokerHandlerName) {
		this.invokerHandlerName = invokerHandlerName;
	}

	@Override
	public void regist(IInvoker iInvoker) {
		invokers.add(iInvoker);
	}

	@Override
	public void remove(IInvoker invoker) {
		invokers.remove(invoker);
	}

	@Override
	public List<IInvoker> getInvokers() {
		return this.invokers;
	}

}
