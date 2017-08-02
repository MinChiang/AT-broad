package com.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.SystemConfig;
import com.frame.system.AbstractServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.transaction.HttpTransaction;

/**
 * @author MinChiang
 *
 * @date 2017年3月8日
 * 
 *
 */
public class HTTPServer extends AbstractServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

	public static final int DEFAULTTHREADNUM = 10;
	public static final int DYNASTICPOLICY = 0;
	public static final int STATICPOLICY = 1;

	private HttpServer httpServer;

	public HTTPServer(SystemConfig systemConfig) throws IOException {
		super(systemConfig);
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				httpServer.stop(0);
				isStart.set(false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.system.IServerOrClient#init()
	 */
	@Override
	public void afterInit() throws IOException {
		this.httpServer = HttpServer.create(new InetSocketAddress(systemConfig.getPort()), 0);
		httpServer.createContext(systemConfig.getContext(), new DefaultHandler());
		httpServer.setExecutor(executor);
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
			LOGGER.info("已经开启对{}的HTTP监听，端口为：{}", systemConfig.getSystemName(), systemConfig.getPort());
		}
		httpServer.start();
	}

	private class DefaultHandler implements HttpHandler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.
		 * HttpExchange)
		 */
		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			new HttpTransaction(httpExchange, systemConfig, counter, contextKeeper.getContext()).frame();
		}
	}

}
