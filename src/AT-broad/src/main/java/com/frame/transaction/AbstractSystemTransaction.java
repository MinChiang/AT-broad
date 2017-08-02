package com.frame.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.SystemConfig;
import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.counter.ICounter;
import com.frame.invokerhandler.IInvokerHandler;

public abstract class AbstractSystemTransaction implements ISystemTransaction {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSystemTransaction.class);
	protected IInvokerHandler invokerHandler;
	protected IContext context;
	protected SystemConfig systemConfig;
	protected ICounter counter;

	protected long startTime;
	protected long stopTime;

	@Override
	public void run() {
		frame();
	}

	@Override
	public void frame() {
		begin();
		prepare();
		read();
		invokerHandler.begin(context);
		write(((String) context.getData(DataConstant.WRITEMESSAGE)).getBytes());
		recycle();
		end();
	}

	public void begin() {
		// 记录交易的开始时间
		startTime = System.currentTimeMillis();
	}

	public void end() {
		// 记录交易结束时间
		stopTime = System.currentTimeMillis();
		long useTime = stopTime - startTime;
		counter.addTime(useTime);
		counter.increment();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("第{}个接收的交易，所用处理时间为：{}", counter.getTotalCount(), useTime);
		}
	}

}
