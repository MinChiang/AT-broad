package com.frame.monitor;

import com.frame.paramhandler.IParamHandler;

public interface IMonitor extends Runnable {

	String input();

	void register(IParamHandler handler);

	String[] handle(String arg);

	void cancel();
}
