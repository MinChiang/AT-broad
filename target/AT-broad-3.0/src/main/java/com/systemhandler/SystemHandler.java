package com.systemhandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.SystemConfig;
import com.configloadermanager.ConfigLoaderManager;
import com.frame.system.IServerOrClient;
import com.util.ReflectUtil;

/**
 * 负责对system的服务器进行控制，提供管理system服务器的唯一入口
 * 
 * @author MinChiang
 *
 * @date 2017年1月23日
 * 
 */
public class SystemHandler extends AbstractSystemHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemHandler.class);

	private static Map<String, IServerOrClient> systemPool = new HashMap<String, IServerOrClient>();
	private static List<SystemConfig> systemConfigs;
	private static SystemHandler systemHandler;

	public static SystemHandler getInstance() {
		if (systemHandler == null) {
			systemHandler = new SystemHandler();
		}
		return systemHandler;
	}

	private SystemHandler() {
		super();
		initSystemPool();
	}

	protected boolean proccess(String[] args) {
		// 获取输入系统的名称
		String systemName = args[1];
		// 获取控制指令
		String ctl = args[0];
		if (START.equalsIgnoreCase(ctl)) {
			// 开始system服务器
			startSystemControllers(new String[] { systemName });
			return true;
		} else if (STOP.equalsIgnoreCase(ctl)) {
			IServerOrClient sc = systemPool.get(systemName);
			if (sc != null && sc.isStart()) {
				stopSystemControllers(new String[] { systemName });
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验输入systemconfig的合法性
	 * 
	 * @param sc
	 *            待检验的systemconfig
	 * @return systemconfig的合法性
	 */
	public static boolean check(SystemConfig sc) {
		String str = sc.getSystemName();
		if (str == null || "".equals(str)) {
			return false;
		}
		int port = sc.getPort();
		if (port <= 0 || port >= 65536) {
			return false;
		}
		return true;
	}

	public void registerServer(SystemConfig systemConfig) throws ClassNotFoundException {
		String impClass = systemConfig.getImpClass();
		try {
			Class<?> cls = Class.forName(impClass);
			IServerOrClient serverOrClient = ReflectUtil.newInstance(cls, new Class[] { SystemConfig.class },
					new Object[] { systemConfig });
			systemPool.put(systemConfig.getSystemName(), serverOrClient);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("无法解析配置{}", impClass);
			}
		}

	}

	/**
	 * 生成系统应用池对象
	 */
	@SuppressWarnings("unchecked")
	public void initSystemPool() {
		systemConfigs = (List<SystemConfig>) ConfigLoaderManager.getInstance().getConfigLoader("serverConfigLoader")
				.toList();
		for (SystemConfig sc : systemConfigs) {
			if (check(sc)) {
				try {
					registerServer(sc);
				} catch (ClassNotFoundException e) {
					LOGGER.error("{}无法加载实现类：{}", sc.getSystemName(), sc.getImpClass());
					e.printStackTrace();
				}
			} else {
				LOGGER.error("{}配置错误", sc.getSystemName());
			}
		}
	}

	/**
	 * 批量停止system服务器
	 * 
	 * @param systems
	 *            对应停止系统的名称
	 */
	public void stopSystemControllers(String[] systems) {
		IServerOrClient serverOrClient = null;
		for (String system : systems) {
			serverOrClient = systemPool.get(system);
			if (serverOrClient != null) {
				try {
					serverOrClient.stop();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 批量开始system服务器
	 * 
	 * @param systems
	 *            对应开始系统的名称
	 */
	public void startSystemControllers(String[] systems) {
		IServerOrClient serverOrClient = null;
		for (String system : systems) {
			serverOrClient = systemPool.get(system);
			if (serverOrClient == null) { // 如果在system池中找不到
				for (SystemConfig sc : systemConfigs) {
					if (sc != null) {
						try {
							registerServer(sc);
						} catch (ClassNotFoundException e) {
							LOGGER.error("无法加载配置：" + sc.getImpClass());
							e.printStackTrace();
						}
					}
				}
			} else { // 如果找到
				new Thread(serverOrClient, "Thread-" + serverOrClient.getSystemConfig().getSystemName()).start();
			}
		}
	}

	/**
	 * 启动所有system服务器
	 */
	public void startAllControllers() {
		Set<String> set = systemPool.keySet();
		String[] systems = set.toArray(new String[] {});
		startSystemControllers(systems);
	}
}
