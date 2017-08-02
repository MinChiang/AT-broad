package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "linux")
public class LinuxConfig {
	private String name;
	private String host;
	private String username;
	private String password;
	private String basePath;
	private String cluster;

	public LinuxConfig(String name, String host, String username, String password, String basePath, String cluster) {
		super();
		this.name = name;
		this.host = host;
		this.username = username;
		this.password = password;
		this.basePath = basePath;
		this.cluster = cluster;
	}

	public LinuxConfig() {
		super();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	@XmlElement(name = "host")
	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	@XmlElement(name = "username")
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getBasePath() {
		return basePath;
	}

	@XmlElement(name = "basepath")
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	@Override
	public String toString() {
		return "LinuxConfig [name=" + name + ", host=" + host + ", username=" + username + ", password=" + password
				+ ", basePath=" + basePath + "]";
	}

}
