package com.transfer.impl;

import com.transfer.ITransfer;
import com.util.FileUtil;
import com.util.ResourceUtil;
import com.util.SSHUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author MinChiang
 * @create 2017-04-26-下午 10:46
 */
public class ScpTransfer implements ITransfer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScpTransfer.class);

	private String user;
	private String password;
	private String ip;
	private int port;

	private String scpUser;
	private String scpIp;

	public ScpTransfer(String user, String password, String ip, int port) {
		this.user = user;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setScpConfig(String user, String ip) {
		this.scpUser = user;
		this.scpIp = ip;
	}

	@Override
	public boolean transfer(String srcFile, String destFile) {
		File file = new File(srcFile);
		String cmd = "scp -o GSSAPIAuthentication=no " + srcFile + " " + scpUser + "@" + scpIp + ":" + destFile;
		SSHUtil sshUtil = new SSHUtil();
		sshUtil.connect(user, password, ip, port);
		String result = sshUtil.execCMD(cmd);
		sshUtil.close();
		return false;
	}
}
