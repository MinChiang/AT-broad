package com.configloadermanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.ConfigLoaderBean;
import com.frame.config.IConfigLoader;
import com.util.FileUtil;
import com.util.JAXBUtil;
import com.util.ReflectUtil;

/**
 * 配置管理类，为配置类提供一个统一的管理类 配置文件：config/configLoaderManager.xml
 *
 * @author MinChiang
 * @date 2017年1月23日
 */
public class ConfigLoaderManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoaderManager.class);
	private static final String DEFAULTFILE = "./config/configloader/configLoaderManager.xml";
	// private static ConfigLoaderManager configLoaderManager;
	private static Map<String, IConfigLoader<?, ?>> configLoaders;
	private static TreeMap<String, IConfigLoader<?, ?>> startupMap;

	private ConfigLoaderManager() {
		configLoaders = new HashMap<String, IConfigLoader<?, ?>>();
		startupMap = new TreeMap<String, IConfigLoader<?, ?>>();
		try {
			load();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void load() throws FileNotFoundException, ClassNotFoundException {
		File file = ResourceUtil.getResource(DEFAULTFILE);
		if (!file.exists()) {
			throw new FileNotFoundException("配置文件" + DEFAULTFILE + "加载失败");
		}
		JAXBUtil jaxbUtil = new JAXBUtil(ConfigLoaderBean.class);
		Collection<ConfigLoaderBean> configLoaderBeans = jaxbUtil.toCollection(FileUtil.read(file));
		for (ConfigLoaderBean clb : configLoaderBeans) {
			IConfigLoader<?, ?> iConfigLoader = ReflectUtil.newInstance(Class.forName(clb.getCls()),
					new Class<?>[]{String.class}, new Object[]{clb.getConfigPath()});
			configLoaders.put(clb.getName(), iConfigLoader);
			startupMap.put(clb.getNo(), iConfigLoader);
		}
	}

	public static ConfigLoaderManager getInstance() {
		// if (configLoaderManager == null) {
		// try {
		// configLoaderManager = new ConfigLoaderManager();
		// } catch (Exception e) {
		// e.printStackTrace();
		// LOGGER.error("加载文件失败");
		// }
		// }
		// return configLoaderManager;
		return ConfigLoaderManagerHolder.instance;
	}

	public IConfigLoader<?, ?> getConfigLoader(String key) {
		return configLoaders.get(key);
	}

	public void batchLoad() throws FileNotFoundException {
		// 启动时需要使用startMap进行启动
		Entry<?, ?> entry = startupMap.pollFirstEntry();
		while (entry != null) {
			IConfigLoader<?, ?> configLoader = (IConfigLoader<?, ?>) entry.getValue();
			configLoader.load();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("加载第{}个配置：{}", entry.getKey(), entry.getValue());
			}
			entry = startupMap.pollFirstEntry();
		}
	}

	private static class ConfigLoaderManagerHolder {
		private static ConfigLoaderManager instance = new ConfigLoaderManager();
	}
}
