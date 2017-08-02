package com.transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.bean.SystemConfig;
import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.counter.ICounter;
import com.sun.net.httpserver.HttpExchange;

/**
 * @author MinChiang
 *
 * @date 2017年3月8日
 * 
 *
 */
public class HttpTransaction extends SystemTransaction {

	private HttpExchange httpExchange;

	/**
	 * @param systemConfig
	 * @param counter
	 * @param context
	 * @throws IOException
	 */
	public HttpTransaction(HttpExchange httpExchange, SystemConfig systemConfig, ICounter counter, IContext context)
			throws IOException {
		super(systemConfig, counter, context);
		this.httpExchange = httpExchange;
		this.is = httpExchange.getRequestBody();
		// this.os = httpExchange.getResponseBody();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.transaction.SystemTransaction#injectInputAndOutputStream(com.frame.
	 * context.IContext)
	 */
	@Override
	protected void injectInputAndOutputStream(IContext context) {
		context.putData(DataConstant.INPUTSTEAM, is);
		context.putData(DataConstant.OUTPUTSTEAM, new ByteArrayOutputStream(1024));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.transaction.ISystemTransaction#read()
	 */
	@Override
	public byte[] read() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.transaction.ISystemTransaction#write(byte[])
	 */
	@Override
	public void write(byte[] content) {
		try {
			httpExchange.sendResponseHeaders(200, content.length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.transaction.SystemTransaction#releaseStream()
	 */
	@Override
	protected void releaseStream() {
		httpExchange.close();
	}

}
