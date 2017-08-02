package com.transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.bean.SystemConfig;
import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.counter.ICounter;

/**
 * @author MinChiang
 *
 * @date 2017年2月26日
 * 
 *
 */

public class TCPTransaction extends SystemTransaction {

	private Socket socket;

	/**
	 * @param socket
	 * @param systemConfig
	 * @param counter
	 * @param context
	 * @throws IOException
	 */
	public TCPTransaction(Socket socket, SystemConfig systemConfig, ICounter counter, IContext context)
			throws IOException {
		super(systemConfig, counter, context);
		this.socket = socket;
		this.is = socket.getInputStream();
		this.os = socket.getOutputStream();
	}

	@Override
	public void releaseStream() {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.transaction.SystemTransaction#injectInputAndOutputStream()
	 */
	@Override
	protected void injectInputAndOutputStream(IContext context) {
		context.putData(DataConstant.INPUTSTEAM, is);
		context.putData(DataConstant.OUTPUTSTEAM, os);
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			os.write(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
