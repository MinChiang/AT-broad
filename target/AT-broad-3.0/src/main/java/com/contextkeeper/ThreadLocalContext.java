/**
 * 
 */
package com.contextkeeper;

import java.util.Map;

import com.context.Context;
import com.frame.context.IContext;
import com.frame.contextkeeper.IContextKeeper;

/**
 * @author MinChiang
 *
 * @date 2017年2月18日
 * 
 *
 */
public class ThreadLocalContext implements IContextKeeper {

	private ThreadLocal<IContext> keeper;
	// private static ThreadLocalContext threadLocalContext;

	/**
	 * 在进行实例化时，传入默认的上下文，在线程获取上下文时使用该上下文的配置，方便使用msgPolicy为static的情况
	 * 
	 * @param systemConfig
	 * @param context
	 */
	public ThreadLocalContext(final Map<String, Object> map) {
		keeper = new ThreadLocal<IContext>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.ThreadLocal#initialValue()
			 */
			@Override
			protected IContext initialValue() {
				return new Context(map);
			}
		};
	}

	public ThreadLocalContext(final String key, final String value) {
		keeper = new ThreadLocal<IContext>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.ThreadLocal#initialValue()
			 */
			@Override
			protected IContext initialValue() {
				return new Context(key, value);
			}
		};
	}

	public ThreadLocalContext() {
		keeper = new ThreadLocal<IContext>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.ThreadLocal#initialValue()
			 */
			@Override
			protected IContext initialValue() {
				return new Context();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.contextkeeper.IContextKeeper#getContext()
	 */
	@Override
	public IContext getContext() {
		return keeper.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.contextkeeper.IContextKeeper#getContext(java.util.Map)
	 */
	@Override
	public IContext getContext(Map<String, Object> map) {
		IContext context = keeper.get();
		context.putData(map);
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frame.contextkeeper.IContextKeeper#returnContext(com.frame.context.
	 * IContext, boolean)
	 */
	@Override
	public boolean returnContext(IContext context, boolean clearFlag) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.contextkeeper.IContextKeeper#close()
	 */
	@Override
	public void close() {
		keeper.set(null);
		keeper = null;
	}
}
