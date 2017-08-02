package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "enviroment")
public class EnviromentConfig {
	private String name;
	private DataBaseConfig dataBaseConfig;
	private LinuxConfig linuxConfig;

	public EnviromentConfig(String name, DataBaseConfig dataBaseConfig, LinuxConfig linuxConfig) {
		super();
		this.name = name;
		this.dataBaseConfig = dataBaseConfig;
		this.linuxConfig = linuxConfig;
	}

	public EnviromentConfig() {
		super();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public DataBaseConfig getDataBaseConfig() {
		return dataBaseConfig;
	}

	@XmlElement(name = "database")
	public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	public LinuxConfig getLinuxConfig() {
		return linuxConfig;
	}

	@XmlElement(name = "linux")
	public void setLinuxConfig(LinuxConfig linuxConfig) {
		this.linuxConfig = linuxConfig;
	}

	@Override
	public String toString() {
		return "EnviromentConfig [name=" + name + ", dataBaseConfig=" + dataBaseConfig + ", linuxConfig=" + linuxConfig
				+ "]";
	}

}
