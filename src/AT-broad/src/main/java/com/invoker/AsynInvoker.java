package com.invoker;

import java.util.Collection;

import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;

public class AsynInvoker extends AbstractInvoker {

	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler,
			IContext context) {
		
	}

	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler,
			IContext context) {

	}

	@Override
	public IInvoker invoke(Collection<IInvoker> invokers,
			IInvokerHandler handler, IContext context) {
		
		return null;
	}

}
