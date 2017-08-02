/**
 * 
 */
package com.frame.io;

import java.io.UnsupportedEncodingException;

/**
 * @author MinChiang
 *
 * @date 2017年2月14日
 * 
 *
 */
public interface IBaseIO {
	
	byte[] read() throws UnsupportedEncodingException;
	
	void write(byte[] content);
	
}
