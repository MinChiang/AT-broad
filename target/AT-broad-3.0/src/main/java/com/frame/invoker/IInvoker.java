package com.frame.invoker;

import java.util.Collection;

import com.frame.context.IContext;
import com.frame.invokerhandler.IInvokerHandler;

public interface IInvoker {

	/**
	 * 开始执行器的动作
	 * 
	 * @param invokers
	 *            执行器容器，该栈保存了本执行器（不包含）后的其他执行器
	 * @param handler
	 *            父级执行器（调用本例的执行器）
	 * @param context
	 *            保存数据的上下文
	 * @return 本例执行器
	 */
	IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context);

	/**
	 * 初始化执行器动作
	 * 
	 * @param invokers
	 *            执行器容器，该栈保存了本执行器（不包含）后的其他执行器
	 * @param handler
	 *            父级执行器（调用本例的执行器）
	 * @param context
	 *            保存数据的上下文
	 */
	void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context);

	/**
	 * 注销执行器动作
	 * 
	 * @param invokers
	 *            执行器容器，该栈保存了本执行器（不包含）后的其他执行器
	 * @param handler
	 *            父级执行器（调用本例的执行器）
	 * @param context
	 *            保存数据的上下文
	 */
	void after(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context);

	/**
	 * 开始执行下一个执行器
	 * 
	 * @param invokers
	 *            执行器容器，该栈保存了本执行器（不包含）后的其他执行器
	 * @param handler
	 *            父级执行器（调用本例的执行器）
	 * @param context
	 *            保存数据的上下文
	 */
	// void next(Collection<IInvoker> invokers, IInvokerHandler handler,
	// IContext context);

	/**
	 * 本方法定义执行器的执行框架，默认为：before->invoke->after
	 * 
	 * @param handler
	 *            执行器容器，该栈保存了本执行器（不包含）后的其他执行器
	 * @param handler
	 *            父级执行器（调用本例的执行器）
	 * @param context
	 *            保存数据的上下文
	 */
	// void invokeFrame(Collection<IInvoker> invokers, IInvokerHandler handler,
	// IContext context);

}
