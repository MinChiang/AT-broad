package com.invoker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.util.FileUtil;
import com.util.ResourceUtil;
import com.util.StreamUtil;

public class AsynReturnInvoker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FixMessageInovker.class);

	private File messageLoc;
	private String host;
	private int port;
	private String charset;

	public File getMessageLoc() {
		return messageLoc;
	}

	public void setMessageLoc(String msgLoc) {
		messageLoc = ResourceUtil.getResource(msgLoc);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler,
			IContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler,
			IContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public IInvoker invoke(Collection<IInvoker> invokers,
			IInvokerHandler handler, IContext context) {
		if (messageLoc != null && messageLoc.isFile() && messageLoc.exists()) {
			String msg = FileUtil.read(messageLoc);
			Socket socket;
			try {
				socket = new Socket(host, port);
				OutputStream os = socket.getOutputStream();
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("开始向host:{},port:{}发送报文:\n{}",host,port,msg);
				}
				StreamUtil.writeWithoutHeadLength(os, msg.getBytes(charset));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("读取文件{}出错", messageLoc.getAbsolutePath());
			}
		}
		return this;
	}

}
