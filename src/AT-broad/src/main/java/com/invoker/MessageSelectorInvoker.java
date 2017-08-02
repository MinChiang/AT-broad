package com.invoker;

import java.util.Collection;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invoker.selectorrule.ISelector;
import com.frame.invokerhandler.IInvokerHandler;
import com.invoker.selector.Selector;
import com.util.XMLUtil;

/**
 * @author MinChiang
 *
 * @date 2017年3月4日
 * 
 *
 */

public class MessageSelectorInvoker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSelectorInvoker.class);
	private ISelector selector = new Selector();
	private String msgPath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#invoke(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		if (msgPath != null && !"".equals(msgPath)) {
			String readMsg = (String) context.getData(DataConstant.READMESSAGE);
			if (readMsg != null) {
				// 把xml报文前面的定长报文头部分去除
				String xmlMsg = readMsg.substring(readMsg.indexOf("<?xml"));
				try {
					XMLUtil xu = new XMLUtil(xmlMsg);
					String value = xu.getDocNode(msgPath).getText();
					String rtnMsg = selector.select(value);
					if (rtnMsg != null && !"".equals(rtnMsg)) {
						context.putData(DataConstant.WRITEMESSAGE, rtnMsg);
					} else {
						if (LOGGER.isErrorEnabled()) {
							LOGGER.error("无法找到交易{}对应的返回报文", value);
						}
					}
				} catch (DocumentException e) {
					e.printStackTrace();
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("选择返回报文出错，原因：\n", e);
					}
				}
			} else {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("异常的接收信息{}");
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#before(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#after(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
	}

}
