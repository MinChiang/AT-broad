/**
 * 
 */
package com.frame.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.frame.context.DataConstant;
import com.frame.context.IContext;

/**
 * @author MinChiang
 *
 * @date 2017年2月14日
 * 
 *
 */
public abstract class Communicator extends BaseIO {

	protected InputStream is;
	protected OutputStream os;
	protected String readEncoding;
	protected String writeEncoding;
	// protected SystemConfig systemConfig;
	// protected IContextKeeper contextKeeper;
	// protected SystemTransaction systemTransaction;
	protected IContext context;

	public Communicator(
			/* SystemConfig systemConfig, IContextKeeper contextKeeper */IContext context) {
		super();
		// this.readEncoding = systemConfig.getReadEncoding();
		// this.writeEncoding = systemConfig.getWriteEncoding();
		// this.systemConfig = systemConfig;
		// this.contextKeeper = contextKeeper;
		// this.context = contextKeeper.getContext();
		// 为上下文注入虚拟输出流
		this.context.putData(DataConstant.OUTPUTSTEAM, new ByteArrayOutputStream(1024));
	}

	public String getReadEncoding() {
		return readEncoding;
	}

	public void setReadEncoding(String readEncoding) {
		this.readEncoding = readEncoding;
	}

	public String getWriteEncoding() {
		return writeEncoding;
	}

	public void setWriteEncoding(String writeEncoding) {
		this.writeEncoding = writeEncoding;
	}

}
