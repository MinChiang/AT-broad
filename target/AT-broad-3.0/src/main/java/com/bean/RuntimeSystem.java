package com.bean;

import java.util.Map;

public class RuntimeSystem {

	private String name;
	private Map<String, Service> services;

	public RuntimeSystem() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Service> getServices() {
		return services;
	}

	public void setServices(Map<String, Service> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "System [name=" + name + ", services=" + services + "]";
	}

}
