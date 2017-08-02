package com.configloader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.bean.Service;
import com.bean.RuntimeSystem;
import com.frame.config.AbstractConfigLoader;
import com.util.XMLUtil;

public class SystemConfigLoader extends AbstractConfigLoader<String, RuntimeSystem> {

	public SystemConfigLoader(String configPath) {
		super(configPath);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(File file) {
		try {
			XMLUtil xmlUtil = new XMLUtil(file);
			List<Node> nodes = xmlUtil.getDocNodes("systems/system");
			for (Node stn : nodes) {
				Element st = (Element) stn;
				String name = st.attributeValue("id");
				Map<String, Service> map = new HashMap<String, Service>();
				List<Node> sern = stn.selectNodes("service");
				for (Node ser : sern) {
					Service service = new Service();
					String servName = ser.getText();
					service.setName(servName);
					map.put(servName, service);
				}
				RuntimeSystem system = new RuntimeSystem();
				system.setName(name);
				system.setServices(map);
				put(name, system);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.frame.config.IConfigLoader#writeToFile(java.lang.Object)
	 */
	@Override
	public boolean writeToFile(RuntimeSystem t) {
		
		return false;
	}

}
