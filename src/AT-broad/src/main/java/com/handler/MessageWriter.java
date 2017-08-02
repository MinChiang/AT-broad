package com.handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageWriter.class);

	private Charset encoding;
	private int headLength;
	private Writer out;

	public MessageWriter(Charset encoding, int headLength) {
		super();
		this.encoding = encoding;
		this.headLength = headLength;
	}

	public String write(String str) {
		String count = null;
		try {
			count = String.valueOf(str.length());
			while (count.length() < headLength) {
				count = "0" + count;
			}
			// 写出长度头
			for (int i = 0; i < headLength; i++) {
				out.write(count.charAt(i));
			}
			// out.write(count + str);
			// 写出内容
			out.write(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("输出流写出错误，错误原因：\n", e);
			}
		}
		return count;
	}

	public void wrap(OutputStream os) {
		out = new BufferedWriter(new OutputStreamWriter(os, encoding));
	}

	public void close() {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
