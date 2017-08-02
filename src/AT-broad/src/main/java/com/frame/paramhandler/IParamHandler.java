package com.frame.paramhandler;

/**
 * 输入监视器的响应操作器
 * 
 * @author MinChiang
 *
 */
public interface IParamHandler {
	/**
	 * 根据参数处理数据
	 * 
	 * @param args
	 */
	boolean handle(String[] args);

	/**
	 * 获取处理标示
	 * 
	 * @return 标示
	 */
	String[] getHandleFlag();
}
