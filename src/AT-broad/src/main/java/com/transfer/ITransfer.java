package com.transfer;

import java.io.File;

/**
 * Created by MinChiang on 2017/4/26 0026.
 */
public interface ITransfer {

	public static final int GET = 1;
	public static final int PUT = 2;

	boolean transfer(String srcFile, String destFile);

}
