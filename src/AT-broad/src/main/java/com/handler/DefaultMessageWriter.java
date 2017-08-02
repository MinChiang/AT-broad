package com.handler;

import java.nio.charset.Charset;

public class DefaultMessageWriter extends MessageWriter {

	public static final int DEFAULTHEADLENGTH = 8;
	public static final Charset DEFAULTCHARSET = Charset.defaultCharset();

	public DefaultMessageWriter() {
		super(DEFAULTCHARSET, DEFAULTHEADLENGTH);
	}

}
