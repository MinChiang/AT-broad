/**
 * 
 */
package com.protocol;

import java.io.IOException;

import com.frame.protocol.IProtocol;

/**
 * @author MinChiang
 *
 * @date 2017年2月13日
 * 
 *
 */
public class TCPProtocol implements IProtocol {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.protocol.IProtocol#read()
	 */
	@Override
	public byte[] read() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.protocol.IProtocol#write(byte[])
	 */
	@Override
	public void write(byte[] content) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.protocol.IProtocol#start()
	 */
	@Override
	public void start() {

	}

}
