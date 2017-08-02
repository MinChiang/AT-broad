package com.contextkeeper;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.context.Context;
import com.frame.context.IContext;
import com.frame.contextkeeper.IContextKeeper;

/**
 * 上下文管理器，提供一系列的管理上下文的方法，包括生产、归还上下文
 * 
 * @author MinChiang
 *
 */
public class BlockingQueueContextKeeper implements IContextKeeper {

	private static final Logger LOG = Logger.getLogger(BlockingQueueContextKeeper.class);

	public static final int DEFAULTCONTEXTNUM = 5;
	public static final int DEFAULTTIMEOUT = 5000;

	public static int contextNum = DEFAULTCONTEXTNUM;
	public static int timeout = DEFAULTTIMEOUT;

	private static IContextKeeper contextKeeper;
	private static BlockingQueue<IContext> contexts;

	private BlockingQueueContextKeeper() {
		init();
	}

	public static IContextKeeper getInstance() {
		if (contextKeeper == null) {
			contextKeeper = new BlockingQueueContextKeeper();
		}
		return contextKeeper;
	}

	@Override
	public IContext getContext(Map<String, Object> map) {
		IContext context = this.getContext();
		context.putData(map);
		return context;
	}

	@Override
	public IContext getContext() {
		IContext context = null;
		try {
			context = contexts.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOG.debug("获取上下文超时，基准超时时间：" + timeout + "，将直接获取上下文");
			context = new Context();
		}
		return context;
	}

	@Override
	public boolean returnContext(IContext context, boolean clearFlag) {
		boolean result = true;
		if (clearFlag) {
			context.clear();
		}
		try {
			contexts.offer(context, timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public void close() {
		contexts.clear();
		contexts = null;
		contextKeeper = null;
	}

	private void init() {
		contexts = new ArrayBlockingQueue<IContext>(contextNum);
		for (int i = 0; i < contextNum; i++) {
			contexts.add(new Context());
		}
	}

}
