/**
 * 
 */
package com.frame.system;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bean.SystemConfig;
import com.contextkeeper.ThreadLocalContext;
import com.counter.Counter;
import com.frame.contextkeeper.IContextKeeper;
import com.frame.counter.ICounter;

/**
 * @author MinChiang
 *
 * @date 2017年2月14日
 * 
 *
 */
public abstract class AbstractServer implements IServerOrClient {

	public static final int DEFAULTTHREADNUM = 10;
	public static final int DYNASTICPOLICY = 0;
	public static final int STATICPOLICY = 1;

	protected AtomicBoolean isStart;
	protected SystemConfig systemConfig;
	protected ICounter counter;
	protected ExecutorService executor;
	protected IContextKeeper contextKeeper;

	/**
	 * 
	 */
	public AbstractServer(SystemConfig systemConfig) throws IOException {
		this.systemConfig = systemConfig;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return this.systemConfig;
	}

	@Override
	public boolean isStart() {
		return isStart.get();
	}

	@Override
	public ICounter getCounter() {
		return this.counter;
	}

	@Override
	public void init() throws IOException {

		String threadPolicy = systemConfig.getThreadpolicy();
		// 配置为固定线程池大小
		if (SystemConfig.FIX.equalsIgnoreCase(threadPolicy)) {
			executor = Executors.newFixedThreadPool(systemConfig.getThreadNum());
		} else if (SystemConfig.CACHED.equalsIgnoreCase(threadPolicy)) { // 配置为缓冲线程池
			executor = Executors.newCachedThreadPool();
		} else { // 如果没有设定，则默认为固定大小
			executor = Executors.newFixedThreadPool(DEFAULTTHREADNUM);
		}

		this.contextKeeper = new ThreadLocalContext();
		this.isStart = new AtomicBoolean(false);
		this.counter = new Counter(systemConfig.getSystemName());
		afterInit();
	}

	abstract protected void afterInit() throws IOException;

}
