package com.app;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.configloadermanager.ConfigLoaderManager;
import com.systemhandler.SystemHandler;

public class ServerStartup {

	private static final Logger LOG = LoggerFactory.getLogger(ServerStartup.class);

	private static SystemHandler systemHandler;

	public static void init() {
		// 加载配置
		try {
			ConfigLoaderManager.getInstance().batchLoad();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		systemHandler = SystemHandler.getInstance();
		systemHandler.startAllControllers();
	}

	public static void main(String[] args) {
		LOG.debug("正在进行初始化操作");
		init();
		LOG.debug("正在对监控程序初始化操作");
	}

}
