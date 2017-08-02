/**
 * 
 */
package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.SystemConfig;
import com.frame.system.AbstractServer;
import com.transaction.TCPTransaction;

/**
 * @author MinChiang
 *
 * @date 2017年2月18日
 * 
 *
 */

public class TCPServer extends AbstractServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPServer.class);

	private ServerSocket serverSocket;

	public TCPServer(SystemConfig systemConfig) throws IOException {
		super(systemConfig);
		this.systemConfig = systemConfig;
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.io.Communicator#init()
	 */
	@Override
	public void afterInit() throws IOException {
		this.serverSocket = new ServerSocket(systemConfig.getPort());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.system.IServerOrClient#stop()
	 */
	@Override
	public void stop() throws IOException {
		if (isStart.get()) {
			synchronized (TCPServer.class) {
				executor.shutdownNow();
				serverSocket.close();
				isStart.set(false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		isStart.set(true);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("已经开启对{}的TCP监听，端口为：{}", systemConfig.getSystemName(), systemConfig.getPort());
		}
		do {
			try {
				Socket socket = serverSocket.accept();
				executor.execute(new TCPTransaction(socket, systemConfig, counter, contextKeeper.getContext()));
			} catch (IOException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("关闭{}监听", systemConfig.getSystemName());
				}
			}
		} while (isStart());
	}

}
