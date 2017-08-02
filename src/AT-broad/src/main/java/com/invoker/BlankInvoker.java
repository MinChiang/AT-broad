package com.invoker;

import java.util.Collection;

import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;

public class BlankInvoker extends AbstractInvoker {

	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		return this;
	}

	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		return;
	}

	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		return;
	}
}
