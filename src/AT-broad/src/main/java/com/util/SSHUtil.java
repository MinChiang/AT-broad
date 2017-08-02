package com.util;

import com.jcraft.jsch.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class SSHUtil {

	public static int readLen = 1024;
	private String charset = "UTF-8";
	private int defaulttimeout = 3000;
	private static JSch jSch;
	private Session session;

	public boolean connect(String user, String password, String ip, int port) {
		jSch = new JSch();
		try {
			session = jSch.getSession(user, ip, port);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setTimeout(defaulttimeout);
			session.setPassword(password);
			session.connect();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session.isConnected();
	}

	public String execCMD(String cmd) {
		ChannelExec channelExec = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(cmd);
			channelExec.setInputStream(null);
			channelExec.connect();
			InputStream in = channelExec.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
			char[] chars = new char[readLen];
			int len = 0;
			while ((len = reader.read(chars)) != -1) {
				sb.append(new String(chars, 0, len));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			channelExec.disconnect();
		}
		return sb.toString();
	}

	public boolean upload(String locSrc, String remoteDest) {
		boolean result = false;
		try {
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			File file = new File(locSrc);
			if (file.exists()) {
				channelSftp.put(locSrc, remoteDest, ChannelSftp.OVERWRITE);
				channelSftp.quit();
				result = true;
			}
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean download(String remoteSrc, String locDest) {
		boolean result = false;
		try {
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			File file = new File(locDest);
			if (file.exists() && file.isDirectory()) {
				channelSftp.get(remoteSrc, locDest);
				result = true;
			}
			channelSftp.quit();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void close() {
		session.disconnect();
	}

}
