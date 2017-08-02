/**
 * 
 */
package com.bean;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MinChiang
 *
 * @date 2017年2月19日
 * 
 *
 */
@XmlRootElement(name = "invoker")
public class InvokerBean {

	private String invokerName;
	private String invokerClass;

	private Map<String, String> params = new HashMap<String, String>();

	public InvokerBean() {
		super();
	}

	@XmlAttribute(name = "class")
	public String getInvokerClass() {
		return invokerClass;
	}

	public void setInvokerClass(String invokerClass) {
		this.invokerClass = invokerClass;
	}

	@XmlAttribute(name = "name")
	public String getInvokerName() {
		return invokerName;
	}

	public void setInvokerName(String invokerName) {
		this.invokerName = invokerName;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
