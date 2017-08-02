package com.handler;

import java.nio.charset.Charset;

public class DefaultMessageGetter extends MessageGetter {

	public static final int DEFAULTHEADLENGTH = 8;
	public static final Charset DEFAULTCHARSET = Charset.defaultCharset();

	public DefaultMessageGetter() {
		super(DEFAULTCHARSET, DEFAULTHEADLENGTH);
	}

}
