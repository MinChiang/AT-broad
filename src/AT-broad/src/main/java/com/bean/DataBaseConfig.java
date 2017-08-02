package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "database")
public class DataBaseConfig {
	private String name;
	private String username;
	private String password;
	private String url;
	private String driver;

	public DataBaseConfig(String name, String username, String password, String url, String driver) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.url = url;
		this.driver = driver;
	}

	public DataBaseConfig() {
		super();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	@XmlElement(name = "user")
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

	public String getUrl() {
		return url;
	}

	@XmlElement(name = "url")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	@XmlElement(name = "driver")
	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "DataBaseConfig [name=" + name + ", username=" + username + ", password=" + password + ", url=" + url
				+ ", driver=" + driver + "]";
	}

}
