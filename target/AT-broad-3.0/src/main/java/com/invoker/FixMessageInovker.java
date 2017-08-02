package com.invoker;

import java.io.File;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.util.FileUtil;
import com.util.ResourceUtil;

/**
 * @author MinChiang
 *
 * @date 2017年3月19日
 * 
 *
 */
public class FixMessageInovker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixMessageInovker.class);
	private boolean cache;
	private File messageLoc;
	private String cacheMsg;

	public File getMessageLoc() {
		return messageLoc;
	}

	public void setMessageLoc(String msgLoc) {
		messageLoc = ResourceUtil.getResource(msgLoc);
		if (messageLoc != null && messageLoc.isFile() && messageLoc.exists()) {
			cacheMsg = FileUtil.read(messageLoc);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.IInvoker#invoke(java.util.Collection,
	 * com.frame.invokerhandler.IInvokerHandler, com.frame.context.IContext)
	 */
	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		// 如果使用了缓冲报文
		if (cache) {
			context.putData(DataConstant.WRITEMESSAGE, cacheMsg);
		} else {	// 如果没有设置缓冲报文，则每次读取一次报文
			if (messageLoc != null && messageLoc.isFile() && messageLoc.exists()) {
				String msg = FileUtil.read(messageLoc);
				context.putData(DataConstant.WRITEMESSAGE, msg);
			} else {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("读取文件{}出错", messageLoc.getAbsolutePath());
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
