package com.frame.invokerhandler;

import java.util.List;

import com.frame.context.IContext;
import com.frame.invoker.IInvoker;

/**
 * 适配流程接口
 * 
 * @author MinChiang
 *
 * @date 2017年1月24日
 * 
 *
 */
public interface IInvokerHandler {

	/**
	 * 在执行控制器中注册执行器
	 * 
	 * @param iInvoker
	 *            需要注册的执行器
	 */
	void regist(IInvoker iInvoker);

	/**
	 * 在执行控制器中注销执行器
	 * 
	 * @param invoker
	 *            需要注销的执行器
	 */
	void remove(IInvoker invoker);

	/**
	 * 开始执行控制器
	 * 
	 * @param context
	 *            数据上下文
	 */
	void begin(IContext context);

	/**
	 * 获取适配流程名称
	 * 
	 * @return 适配流程的名称
	 */
	String getInvokerHandlerName();

	/**
	 * 设置适配流程名称
	 * 
	 * @param invokerHandlerName
	 *            适配流程名称
	 */
	void setInvokerHandlerName(String invokerHandlerName);

	/**
	 * 获取执行器
	 * 
	 * @return 在适配流程中获取执行器
	 */
	List<IInvoker> getInvokers();
}
