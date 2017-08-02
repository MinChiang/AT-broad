package com.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.io.CalcedReader;

public class MessageGetter {

	private static final int DEFAULTCACHE = 1024;

	private Charset encoding;
	private int headLength;
	private CalcedReader cr;

	public MessageGetter(Charset encoding, int headLength) {
		super();
		this.encoding = encoding;
		this.headLength = headLength;
	}

	public String read() {
		char[] chars = new char[DEFAULTCACHE];
		int len;
		StringBuffer sb = new StringBuffer();
		try {
			while ((len = cr.read(chars)) != -1) {
				sb.append(chars, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String getLength() {
		return String.valueOf(cr.getLength());
	}

	public String getOrginalLength() {
		return new String(cr.getHead());
	}

	public void wrap(InputStream is) {
		cr = new CalcedReader(new BufferedReader(new InputStreamReader(is, encoding)), headLength);
	}

	public void close() {
		if (cr != null) {
			try {
				cr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
