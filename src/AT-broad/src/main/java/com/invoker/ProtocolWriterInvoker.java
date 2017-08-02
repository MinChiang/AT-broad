package com.invoker;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.handler.MessageWriter;

/**
 * 写出执行器
 *
 * @author MinChiang
 * @date 2017年1月24日
 */

public class ProtocolWriterInvoker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolWriterInvoker.class);
	private Charset encoding;
	private int headLength;

	public ProtocolWriterInvoker() {
		super();
	}

	public ProtocolWriterInvoker(Charset encoding, int headLength) {
		super();
	}

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = Charset.forName(encoding);
	}

	public int getHeadLength() {
		return headLength;
	}

	public void setHeadLength(int headLength) {
		this.headLength = headLength;
	}

	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		// 每个线程均创建一个对象
		MessageWriter messageWriter = new MessageWriter(this.encoding, this.headLength);
		OutputStream os = (OutputStream) context.getData(DataConstant.OUTPUTSTEAM);
		if (os != null) {
			messageWriter.wrap(os);
		}
		String message = (String) context.getData(DataConstant.WRITEMESSAGE);
		String writeLen = messageWriter.write(message);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("写出的长度头为:{}，报文为:\n{}", writeLen, message);
		}
		context.putData(DataConstant.WRITELENGTH, writeLen);
		return this;
	}

	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {

	}

	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {

	}

}
