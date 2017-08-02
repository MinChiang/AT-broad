package com.transfer.impl;

import com.transfer.ITransfer;
import com.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.DestroyFailedException;
import java.io.File;

/**
 * @author MinChiang
 * @create 2017-04-26-下午 10:39
 */
public class NfsTransfer implements ITransfer {

	private static final Logger LOGGER = LoggerFactory.getLogger(NfsTransfer.class);

	public NfsTransfer() {
	}

	@Override
	public boolean transfer(String srcFile, String destFile) {
		File file = new File(srcFile);
		if (file.exists() && file.isFile()) {
			FileUtil.copyFile(file, new File(destFile));
			return true;
		}
		return false;
	}
}
