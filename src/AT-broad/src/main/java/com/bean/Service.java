package com.bean;

import java.io.Serializable;

/**
 * @author MinChiang
 *
 * @date 2017年1月27日
 * 
 *
 */
public class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7447667233006643626L;

	private String name;
	private String protocolName;
	private String adapterName;
	private String systemName;
	private String channelName;

	public Service() {
		super();
	}

	public Service(String name, String protocolName, String adapterName, String systemName, String channelName) {
		super();
		this.name = name;
		this.protocolName = protocolName;
		this.adapterName = adapterName;
		this.systemName = systemName;
		this.channelName = channelName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public String toString() {
		return "Service [name=" + name + ", protocolName=" + protocolName + ", adapterName=" + adapterName
				+ ", systemName=" + systemName + ", channelName=" + channelName + "]";
	}

}
