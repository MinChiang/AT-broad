package com.configloader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.bean.SystemConfig;
import com.frame.config.AbstractConfigLoader;
import com.util.FileUtil;
import com.util.JAXBUtil;

public class ServerConfigLoader extends AbstractConfigLoader<String, SystemConfig> {

	public ServerConfigLoader(String configPath) {
		super(configPath);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(File file) {
		JAXBUtil ju = new JAXBUtil(SystemConfig.class);
		Collection<SystemConfig> systemConfigs = ju.toCollection(FileUtil.read(file));
		for (SystemConfig sc : systemConfigs) {
			put(sc.getSystemName(), sc);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.config.IConfigLoader#writeToFile(java.lang.Object)
	 */
	@Override
	public boolean writeToFile(SystemConfig t) {
		JAXBUtil ju = new JAXBUtil(SystemConfig.class);
		boolean result = true;
		File file = new File(configPath);
		List<SystemConfig> list = toList();
		String xml = ju.toXml(list, "systems", "UTF-8");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(xml);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
