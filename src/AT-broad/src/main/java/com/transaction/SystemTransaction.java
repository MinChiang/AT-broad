package com.transaction;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.bean.SystemConfig;
import com.frame.context.IContext;
import com.frame.counter.ICounter;
import com.frame.transaction.AbstractSystemTransaction;
import com.invokerhandler.InvokerHandlerFactory;

/**
 * 交易处理器
 * 
 * @author MinChiang
 *
 * @date 2017年1月23日
 * 
 */
public abstract class SystemTransaction extends AbstractSystemTransaction {

	protected InputStream is;
	protected OutputStream os;

	public SystemTransaction(SystemConfig systemConfig, ICounter counter, IContext context) throws IOException {
		super();
		this.systemConfig = systemConfig;
		this.counter = counter;
		this.context = context;
		this.invokerHandler = InvokerHandlerFactory.getInstance().getInvokerHandler(systemConfig.getInvokerHandler());
	}

	@Override
	public void prepare() {
		injectInputAndOutputStream(context);
	}

	@Override
	public void recycle() {
		if (this.is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (this.os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		releaseStream();
	}

	/**
	 * 将真实的输入输出流注入到上下文中
	 * 
	 * @param context
	 *            被注入的上下文
	 */
	protected abstract void injectInputAndOutputStream(IContext context);

	protected abstract void releaseStream();
}
