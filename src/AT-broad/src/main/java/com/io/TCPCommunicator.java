/**
 * 
 */
package com.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.bean.SystemConfig;
import com.context.Context;
import com.frame.context.DataConstant;
import com.frame.contextkeeper.IContextKeeper;
import com.frame.counter.ICounter;
import com.frame.io.Communicator;

/**
 * @author MinChiang
 *
 * @date 2017年2月18日
 * 
 *
 */

public class TCPCommunicator extends Communicator {

	/**
	 * @param systemConfig
	 */
	public TCPCommunicator(Socket socket, SystemConfig systemConfig, ICounter counter, IContextKeeper contextKeeper) {
		// super(systemConfig, contextKeeper);
		super(new Context());
		try {
			this.is = socket.getInputStream();
			this.os = socket.getOutputStream();
			// this.systemTransaction = new TCPTransaction(socket, systemConfig,
			// counter, this.context);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public byte[] read() throws UnsupportedEncodingException {
		return null;
	}

	/**
	 * 需要修改的地方，解决http的问题
	 */
	@Override
	public void write(byte[] content) {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) context.getData(DataConstant.OUTPUTSTEAM);
		try {
			os.write(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
