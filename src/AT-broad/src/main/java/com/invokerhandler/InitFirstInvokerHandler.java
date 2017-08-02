package com.invokerhandler;

import java.util.ArrayList;

import com.frame.context.IContext;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.AbstractInvokerHandler;

/**
 * @author MinChiang
 *
 * @date 2017年3月5日
 * 
 *
 */

public class InitFirstInvokerHandler extends AbstractInvokerHandler {

	public InitFirstInvokerHandler() {
	}

	public InitFirstInvokerHandler(ArrayList<IInvoker> invokers) {
		this.invokers = invokers;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invokerhandler.IInvokerHandler#begin(com.frame.context.
	 * IContext)
	 */
	@Override
	public void begin(IContext context) {
		for (IInvoker invoker : this.invokers) {
			invoker.before(invokers, this, context);
		}
		for (IInvoker invoker : this.invokers) {
			invoker.invoke(invokers, this, context);
			invoker.after(invokers, this, context);
		}
	}

}