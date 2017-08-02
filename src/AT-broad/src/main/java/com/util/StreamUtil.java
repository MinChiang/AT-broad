package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author:MinChiang
 * @date:2017-07-01 21:22
 *                  <p>
 *                  version:V1.0.0 Description:
 */
public class StreamUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StreamUtil.class);

	public static final int BYTESIZE = 1024;

	public static byte[] readWithoutHeadLength(InputStream is, int byteSize,
			boolean autoClose) throws IOException {
		return readWithHeadLength(is, byteSize, 0, autoClose);
	}

	public static byte[] readWithoutHeadLength(InputStream is)
			throws IOException {
		return readWithoutHeadLength(is, BYTESIZE, false);
	}

	public static void writeWithoutHeadLength(OutputStream os, byte[] content,
			boolean autoClose) throws IOException {
		if (os != null) {
			os.write(content);
			if (autoClose) {
				os.close();
			}
		}
	}

	public static void writeWithoutHeadLength(OutputStream os, byte[] content)
			throws IOException {
		writeWithoutHeadLength(os, content, false);
	}

	public static byte[] readWithHeadLength(InputStream is, int byteSize,
			int headLength, boolean autoClose) throws IOException {
		if (is != null && byteSize > 0) {
			int lengthRestrict = 0;
			if (headLength > 0) {
				byte[] headContent = new byte[headLength];
				int len = is.read(headContent);
				if (len < headLength) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("输入的报文（{}）不足报文长度头长度（{}），舍弃报文", len,
								headLength);
					}
					return null;
				}
				try {
					lengthRestrict = Integer.valueOf(new String(headContent));
					if (lengthRestrict == 0) {
						return null;
					}
				} catch (Exception e) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("无效长度头输入：{}", new String(headContent));
					}
					e.printStackTrace();
					return null;
				}
			}
			byte[] bytes = new byte[byteSize];
			int len = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (lengthRestrict > 0) {
				int lengthSum = 0;
				while ((len = is.read(bytes)) != -1) {
					if (lengthSum + len > lengthRestrict) {
						baos.write(bytes, 0, lengthRestrict - lengthSum);
						break;
					} else {
						baos.write(bytes, 0, len);
					}
					lengthSum += len;
				}
			} else {
				while ((len = is.read(bytes)) != -1) {
					baos.write(bytes, 0, len);
				}
			}
			if (autoClose) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return baos.toByteArray();
		}
		return null;
	}

	public static byte[] readWithHeadLength(InputStream is, int headLength)
			throws IOException {
		return readWithHeadLength(is, BYTESIZE, headLength, false);
	}

	public static void writeWithHeadLength(OutputStream os, byte[] content,
			int headLength, boolean autoClose) throws IOException {
		if (os != null) {
			int sum = 0;
			String lenStr = String.valueOf(content.length);
			while (lenStr.length() < headLength) {
				lenStr = "0" + lenStr;
			}
			os.write(lenStr.getBytes());
			os.write(content);
			if (autoClose) {
				os.close();
			}
		}
	}

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("96.0.50.68", 20001);
			OutputStream os = socket.getOutputStream();
			File file = new File(
					"C:\\Users\\kaifaershi09\\Desktop\\14007001_req.xml");
			String msg = FileUtil.read(file, "UTF-8");
			StreamUtil.writeWithoutHeadLength(os, msg.getBytes("UTF-8"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
