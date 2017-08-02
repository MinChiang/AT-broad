/**
 * 
 */
package com.frame.protocol;

import java.io.Closeable;

/**
 * @author MinChiang
 *
 * @date 2017年2月13日
 * 
 *
 */
public interface IProtocol extends Closeable{

	byte[] read();

	void write(byte[] content);
	
	void start();
	
}
