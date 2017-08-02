package com.frame.transaction;

public interface ISystemTransaction extends Runnable {

	/**
	 * 回收交易数据
	 */
	void recycle();

	/**
	 * 初始化交易数据
	 */
	void prepare();

	/**
	 * 定义交易的框架
	 */
	void frame();

	/**
	 * 读取数据
	 * 
	 * @return 读取到的数据
	 */
	byte[] read();

	/**
	 * 写入数据
	 * 
	 * @param content
	 *            待写入的数据
	 */
	void write(byte[] content);

}