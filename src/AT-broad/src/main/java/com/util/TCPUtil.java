/**
 * 
 */
package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author MinChiang
 *
 * @date 2017年2月6日
 * 
 *
 */
public class TCPUtil {

	private static final int INCREMENT = 1024;

	private Socket socket;
	private InputStream is;
	private OutputStream os;

	public TCPUtil(String host, int port) {
		try {
			socket = new Socket(host, port);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			socket.setSoTimeout(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(byte[] bytes) {
		if (!socket.isClosed() && os != null) {
			try {
				os.write(bytes);
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] read() {
		byte[] temp = new byte[INCREMENT];
		int len = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (!socket.isClosed() && is != null) {
			try {
				while ((len = is.read(temp)) != -1) {
					baos.write(temp, 0, len);
				}
				socket.shutdownInput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] process(byte[] bytes) {
		write(bytes);
		byte[] result = read();
		close();
		return result;
	}

}
