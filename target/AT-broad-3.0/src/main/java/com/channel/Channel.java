package com.channel;

import java.util.Map;

import com.bean.Service;

public class Channel {

	private String name;
	private String type;
	private String expression;
	private String charset;
	private String mode;
	private Map<String, Service> services;

	public Channel() {

	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, Service> getServices() {
		return services;
	}

	public void setServices(Map<String, Service> services) {
		this.services = services;
	}

	// @Override
	// public String toString() {
	// return "Channel [name=" + name + ", type=" + type + ", expression=" +
	// expression + ", charset=" + charset
	// + ", mode=" + mode + ", services=" + services + "]";
	// }

}
