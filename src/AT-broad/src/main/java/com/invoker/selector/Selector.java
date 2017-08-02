package com.invoker.selector;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.invoker.selectorrule.ISelector;
import com.util.FileUtil;
import com.util.ResourceUtil;

/**
 * @author MinChiang
 *
 * @date 2017年3月4日
 * 
 *
 */

public class Selector implements ISelector {

	private static final File DEFAULTSELECTDICTIONARY = ResourceUtil.getResource("./message/");
	private static final Logger LOGGER = LoggerFactory.getLogger(Selector.class);
	private File selectDictionary = DEFAULTSELECTDICTIONARY;

	public File getSelectDictionary() {
		return selectDictionary;
	}

	public void setSelectDictionary(String selectDictionary) {
		File file = ResourceUtil.getResource(selectDictionary);
		this.selectDictionary = file;
	}

	public String getDictionary() {
		return selectDictionary.getAbsolutePath();
	}

	public void setDictionary(String selectDictionary) {
		this.selectDictionary = ResourceUtil.getResource(selectDictionary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.invoker.selectorrule.ISelectorRule#select(java.io.File,
	 * java.lang.String)
	 */
	@Override
	public String select(String sel) {
		String result = "";
		if (selectDictionary != null && selectDictionary.exists() && selectDictionary.isDirectory()) {
			File readFile = new File(selectDictionary, sel + ".xml");
			if (readFile.exists()) {
				result = FileUtil.read(readFile);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("无法找到文件{}", readFile);
				}
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("无法找到文件夹{}", selectDictionary);
			}
		}
		return result;
	}

}
