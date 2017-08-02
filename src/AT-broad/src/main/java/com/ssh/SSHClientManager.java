package com.ssh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.SSHClient;
import com.util.FileUtil;
import com.util.JAXBUtil;
import com.util.ResourceUtil;

public class SSHClientManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SSHClientManager.class);
	public static final String DEFAULTCLIENTCONFIG = "./config/SSH-config.xml";

	private static Map<String, SSHClient> clients;

	private SSHClientManager() {
		clients = new HashMap<String, SSHClient>();
		try {
			load();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static SSHClientManager getInstance() {
		return SSHClientManagerHolder.sshClientManager;
	}

	@SuppressWarnings("unchecked")
	public void load() throws FileNotFoundException {
		JAXBUtil jaxbUtil = new JAXBUtil(SSHClient.class);
		File file = ResourceUtil.getResource(DEFAULTCLIENTCONFIG);
		if (!file.exists()) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("文件{}不存在！", file.getAbsolutePath());
			}
		}
		Collection<SSHClient> cs = jaxbUtil.toCollection(FileUtil.read(file));
		for (SSHClient client : cs) {
			clients.put(client.getServerName(), client);
		}
	}

	public void reload() throws FileNotFoundException {
		this.load();
	}

	public SSHClient getSSHClient(String name) {
		return clients.get(name);
	}

	public void registClient(SSHClient client) {
		clients.put(client.getServerName(), client);
	}

	private static class SSHClientManagerHolder {
		private static SSHClientManager sshClientManager = new SSHClientManager();
	}

}
