package com.transfer.impl;

import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.client.FtpPut;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.transfer.ITransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author MinChiang
 * @create 2017-04-27-上午 9:02
 */
public class ApiTransfer implements ITransfer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiTransfer.class);

	public static final int GET = 1;
	public static final int PUT = 2;

	private int action;

	public ApiTransfer(int action) {
		this.action = action;
	}

	@Override
	public boolean transfer(String srcFile, String destFile) {
		boolean result = false;
		if (action != PUT && action != GET) {
			return false;
		}
		if (action == PUT) {
			File file = new File(srcFile);
			if (!file.exists() || !file.isFile()) {
				return false;
			}
			try {
				FtpPut ftpPut = new FtpPut(srcFile, destFile, false, new FtpClientConfigSet(), null);
				ftpPut.doPutFile();
				result = true;
			} catch (FtpException e) {
				e.printStackTrace();
			}
		} else if (action == GET) {
			try {
				FtpGet ftpGet = new FtpGet(srcFile, destFile, false, new FtpClientConfigSet(), null);
				ftpGet.doGetFile();
				result = true;
			} catch (FtpException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
