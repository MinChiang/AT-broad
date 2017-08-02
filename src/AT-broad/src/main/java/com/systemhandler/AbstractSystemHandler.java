package com.systemhandler;

import com.frame.paramhandler.IParamHandler;

/**
 * 对应系统响应器
 * 
 * @author MinChiang
 *
 */
public abstract class AbstractSystemHandler implements IParamHandler {

	protected static final String STOP = "stop";
	protected static final String START = "start";
	protected static final String[] PARAM = new String[] { STOP, START };

	public AbstractSystemHandler() {
		super();
	}

	@Override
	public boolean handle(String[] args) {
		proccess(args);
		return true;
	}

	@Override
	public String[] getHandleFlag() {
		return PARAM;
	}

	protected abstract boolean proccess(String args[]);

}
