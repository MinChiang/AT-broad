package com.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class CalcedReader extends FilterReader {

	private boolean readHeadFinish;
	private char[] head;
	private Reader in;
	private int headLen;

	public CalcedReader(Reader reader, int headLen) {
		super(reader);
		this.headLen = headLen;
		this.in = reader;
		this.head = new char[headLen];
	}

	public int getLength() {
		return readHeadFinish ? Integer.valueOf(new String(head)) : -1;
	}

	@Override
	public int read() throws IOException {
		readHead();
		return super.read();
	}

	@Override
	public int read(char[] b) throws IOException {
		readHead();
		return super.read(b);
	}

	@Override
	public int read(char[] b, int off, int len) throws IOException {
		readHead();
		return super.read(b, off, len);
	}

	public void readHead() throws IOException {
		if (!readHeadFinish && headLen != 0) {
			readHeadFinish = true;
			in.read(head);
		}
	}

	public int getHeadLen() {
		return headLen;
	}

	public void setHeadLen(int headLen) {
		this.headLen = headLen;
	}

	public char[] getHead() {
		return head;
	}

	public void setHead(char[] head) {
		this.head = head;
	}
}
