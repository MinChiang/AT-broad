/**
 * 
 */
package com.configloader;

import java.io.File;
import java.util.Collection;

import com.bean.InvokerHandlerBean;
import com.frame.config.AbstractConfigLoader;
import com.util.FileUtil;
import com.util.JAXBUtil;

/**
 * @author MinChiang
 *
 * @date 2017年2月19日
 * 
 *
 */
public class InvokerHandlerConfigLoader extends AbstractConfigLoader<String, InvokerHandlerBean> {

	/**
	 * @param configPath
	 */
	public InvokerHandlerConfigLoader(String configPath) {
		super(configPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.config.IConfigLoader#writeToFile(java.lang.Object)
	 */
	@Override
	public boolean writeToFile(InvokerHandlerBean invokerHandlerBean) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.config.AbstractConfigLoader#read(java.io.File)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void read(File file) {
		JAXBUtil ju = new JAXBUtil(InvokerHandlerBean.class);
		Collection<InvokerHandlerBean> invokerHandlerBeans = ju.toCollection(FileUtil.read(file));
		for (InvokerHandlerBean invokerHandlerBean : invokerHandlerBeans) {
			put(invokerHandlerBean.getInvokerHandlerName(), invokerHandlerBean);
		}
	}

}
