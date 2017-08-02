/**
 * 
 */
package com.invoker;

import java.io.File;
import java.util.Collection;

import org.dom4j.DocumentException;
import org.dom4j.Node;

import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.util.FileUtil;
import com.util.XMLUtil;

/**
 * 查找报文执行器
 * 
 * @author MinChiang
 *
 * @date 2017年1月24日
 * 
 *
 */

public class ReturnMessageFinderInvoker extends AbstractInvoker {

	private String tradeNoPath;
	private String msgLoc;

	public ReturnMessageFinderInvoker(String tradeNoPath, String msgLoc) {
		super();
		this.tradeNoPath = tradeNoPath;
		this.msgLoc = msgLoc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#invoke(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		XMLUtil xmlUtil = null;
		String writeMsg = null;
		String readMsg = (String) context.getData(DataConstant.READMESSAGE);
		int index = readMsg.indexOf("<?xml ");
		readMsg = readMsg.substring(index);
		try {
			xmlUtil = new XMLUtil(readMsg);
			Node node = xmlUtil.getDocNode(tradeNoPath);
			String tradeNo = node.getText();
			File file = new File(msgLoc);
			if (file.exists() && file.isDirectory() && tradeNo != null && "".equals(tradeNo)) {
				File msgFile = new File(file, tradeNo + ".xml");
				if (msgFile.exists() && msgFile.isFile()) {
					writeMsg = FileUtil.read(msgFile);
					if (writeMsg != null && "".equals(writeMsg)) {
						context.putData(DataConstant.WRITEMESSAGE, writeMsg);
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#before(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		// String message = context.putData(DataConstant.MESSAGE, value);
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
