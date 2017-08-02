package com.invokerhandler;

import java.util.ArrayList;

import com.frame.context.IContext;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.AbstractInvokerHandler;

/**
 * 适配流程
 * 
 * @author MinChiang
 *
 * @date 2017年1月23日
 * 
 *
 */

public class BaseInvokerHandler extends AbstractInvokerHandler {

	public BaseInvokerHandler() {
		super();
	}

	public BaseInvokerHandler(ArrayList<IInvoker> invokers) {
		super();
		this.invokers = invokers;
	}

	@Override
	public void begin(IContext context) {
		for (IInvoker iInvoker : invokers) {
			iInvoker.before(invokers, this, context);
			iInvoker.invoke(invokers, this, context);
			iInvoker.after(invokers, this, context);
		}
	}

}
