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
 * @date 2017年2月13日
 * 
 *       最基本的生成上下文容器
 */
public class BaseContextKeeper implements IContextKeeper {

	private static BaseContextKeeper baseContextKeeper;

	/**
	 * 
	 */
	private BaseContextKeeper() {

	}

	public static BaseContextKeeper getInstance() {
		if (baseContextKeeper == null) {
			baseContextKeeper = new BaseContextKeeper();
		}
		return baseContextKeeper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.contextkeeper.IContextKeeper#getContext()
	 */
	@Override
	public IContext getContext() {
		return new Context();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.contextkeeper.IContextKeeper#getContext(java.util.Map)
	 */
	@Override
	public IContext getContext(Map<String, Object> map) {
		return this.getContext(map);
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
		if (clearFlag) {
			context.clear();
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.contextkeeper.IContextKeeper#close()
	 */
	@Override
	public void close() {
	}
}
