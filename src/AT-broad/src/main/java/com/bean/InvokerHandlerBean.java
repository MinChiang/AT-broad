/**
 * 
 */
package com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MinChiang
 *
 * @date 2017年2月19日
 * 
 *
 */
@XmlRootElement(name = "invokerHandler")
public class InvokerHandlerBean {

	private String invokerHandlerName;
	private List<InvokerBean> invokers = new ArrayList<InvokerBean>();

	public InvokerHandlerBean() {
		super();
	}

	public List<InvokerBean> getInvokers() {
		return invokers;
	}

	@XmlElementWrapper(name = "invokers")
	@XmlElement(name = "invoker")
	public void setInvokers(List<InvokerBean> invokers) {
		this.invokers = invokers;
	}

	public String getInvokerHandlerName() {
		return invokerHandlerName;
	}

	@XmlAttribute(name = "name")
	public void setInvokerHandlerName(String invokerHandlerName) {
		this.invokerHandlerName = invokerHandlerName;
	}

	@Override
	public String toString() {
		return "InvokerHandlerBean [invokerHandlerName=" + invokerHandlerName + ", invokers=" + invokers + "]";
	}

}
