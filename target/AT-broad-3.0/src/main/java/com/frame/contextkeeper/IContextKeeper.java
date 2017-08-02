package com.frame.contextkeeper;

import java.util.Map;

import com.frame.context.IContext;

/**
 * 提供一个统一管理上下文的接口，上下文管理器
 * 
 * @author MinChiang
 *
 */
public interface IContextKeeper {

	/**
	 * 通过池对象获取上下文
	 * 
	 * @return 空闲的上下文
	 */
	IContext getContext();

	/**
	 * 通过池对象获取上下文
	 * 
	 * @param map
	 *            上下文初始内容
	 * @return 空闲的上下文
	 */
	IContext getContext(Map<String, Object> map);

	/**
	 * 归还上下文
	 * 
	 * @param context
	 *            要进行归还的上下文
	 * @param clearFlag
	 *            是否对上下文中设定的内容进行清空
	 * @return 归还成功与否
	 */
	boolean returnContext(IContext context, boolean clearFlag);

	/**
	 * 关闭上下文管理器
	 */
	void close();

}
