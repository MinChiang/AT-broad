package com.invoker;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.handler.DefaultMessageGetter;
import com.handler.MessageGetter;

/**
 * 写入执行器
 *
 * @author MinChiang
 * @date 2017年1月24日
 */

public class ProtocolReaderInvoker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolReaderInvoker.class);
	private Charset encoding;
	private int headLength;

	public ProtocolReaderInvoker() {
		super();
		this.encoding = DefaultMessageGetter.DEFAULTCHARSET;
		this.headLength = DefaultMessageGetter.DEFAULTHEADLENGTH;
	}

	public ProtocolReaderInvoker(Charset encoding, int headLength) {
		super();
		this.encoding = encoding;
		this.headLength = headLength;
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
	public IInvoker invoke(Collection<IInvoker> iInvokers, IInvokerHandler handler, IContext context) {
		// 每个线程均创建一个对象
		MessageGetter messageGetter = new MessageGetter(this.encoding, this.headLength);
		InputStream is = (InputStream) context.getData(DataConstant.INPUTSTEAM);
		if (is != null) {
			messageGetter.wrap(is);
		}
		String message = messageGetter.read();
		String receivelength = messageGetter.getOrginalLength();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("读取的长度头为：{}，报文为：\n{}", receivelength, message);
		}
		context.putData(DataConstant.READMESSAGE, message);
		context.putData(DataConstant.RECEIVELENGTH, receivelength);
		return this;
	}

	@Override
	public void before(Collection<IInvoker> iInvokers, IInvokerHandler handler, IContext context) {

	}

	@Override
	public void after(Collection<IInvoker> iInvokers, IInvokerHandler handler, IContext context) {

	}

}
