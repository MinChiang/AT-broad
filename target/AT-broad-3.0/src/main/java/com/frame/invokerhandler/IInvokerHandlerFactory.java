package com.frame.invokerhandler;

public interface IInvokerHandlerFactory {

	/**
	 * 获取适配流程
	 * 
	 * @param invokerHandlerName
	 *            适配流程名称
	 * @return 适配liucheng
	 */
	public abstract IInvokerHandler getInvokerHandler(String invokerHandlerName);

}