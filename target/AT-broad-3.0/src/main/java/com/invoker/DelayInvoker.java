package com.invoker;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;

/**
 * @author MinChiang
 *
 * @date 2017年3月19日
 * 
 *
 */
public class DelayInvoker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(DelayInvoker.class);

	private long delayTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#invoke(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("在线程{}中进行等待，等待时长：{}", Thread.currentThread().getName(), delayTime);
			}
			Thread.sleep(delayTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#before(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#after(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		
	}

}
