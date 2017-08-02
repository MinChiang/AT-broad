package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SSHClient")
public class SSHClient {

	private String ip;
	private String port;
	private String user;
	private String password;
	private String serverName;

	public SSHClient(String ip, String port, String user, String password, String serverName) {
		super();
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.serverName = serverName;
	}

	public SSHClient() {
		super();
	}

	public String getIp() {
		return ip;
	}

	@XmlElement
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	@XmlElement
	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	@XmlElement
	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return serverName;
	}

	@XmlAttribute(name = "name")
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String toString() {
		return "SSHClient [ip=" + ip + ", port=" + port + ", user=" + user + ", password=" + password + ", serverName="
				+ serverName + "]";
	}
}
