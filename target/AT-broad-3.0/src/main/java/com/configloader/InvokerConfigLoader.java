package com.configloader;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.config.AbstractConfigLoader;
import com.frame.invoker.IInvoker;
import com.util.XML2ObjectUtil;

/**
 * @author MinChiang
 *
 * @date 2017年3月13日
 * 
 *
 */
public class InvokerConfigLoader extends AbstractConfigLoader<String, IInvoker> {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvokerConfigLoader.class);

	/**
	 * @param configPath
	 */
	public InvokerConfigLoader(String configPath) {
		super(configPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.config.IConfigLoader#writeToFile(java.lang.Object)
	 */
	@Override
	public boolean writeToFile(IInvoker t) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.config.AbstractConfigLoader#read(java.io.File)
	 */
	@Override
	public void read(File file) {
		XML2ObjectUtil<IInvoker> xml2ObjectUtil = new XML2ObjectUtil<IInvoker>(file);
		try {
			Map<String, IInvoker> map = xml2ObjectUtil.parse();
			for (Entry<String, IInvoker> entry : map.entrySet()) {
				LOGGER.info("开始加载执行器{}:{}", entry.getKey(),entry.getValue());
				put(entry.getKey(), entry.getValue());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("解析执行器出错，错误原因{}", e);
			}
		}
	}

}
