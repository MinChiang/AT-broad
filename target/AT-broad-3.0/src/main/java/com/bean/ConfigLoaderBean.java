package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configLoader")
public class ConfigLoaderBean {

	private String name;
	private String cls;
	private String configPath;
	private String no;

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public String getCls() {
		return cls;
	}

	@XmlElement(name = "class")
	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getConfigPath() {
		return configPath;
	}

	@XmlElement(name = "configPath")
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public String getNo() {
		return no;
	}

	@XmlAttribute(name = "no")
	public void setNo(String no) {
		this.no = no;
	}

}
