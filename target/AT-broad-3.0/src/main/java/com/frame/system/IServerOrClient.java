/**
 * 
 */
package com.frame.system;

import java.io.IOException;

import com.bean.SystemConfig;
import com.frame.counter.ICounter;

/**
 * @author MinChiang
 *
 * @date 2017年2月14日
 * 
 *
 */
public interface IServerOrClient extends Runnable{

	SystemConfig getSystemConfig();
	
	void stop() throws IOException;
	
	void init() throws IOException;
	
	boolean isStart();
	
	ICounter getCounter();
	
}
