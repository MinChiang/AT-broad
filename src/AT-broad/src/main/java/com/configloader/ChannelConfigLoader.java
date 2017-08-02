package com.configloader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.bean.Service;
import com.channel.Channel;
import com.frame.config.AbstractConfigLoader;
import com.util.XMLUtil;

public class ChannelConfigLoader extends AbstractConfigLoader<String, Channel> {

	public ChannelConfigLoader(String configPath) {
		super(configPath);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(File file) {
		XMLUtil xmlUtil;
		try {
			xmlUtil = new XMLUtil(file);
			List<Node> nodes = xmlUtil.getDocNodes("channels/channel");
			for (Node chn : nodes) {
				Channel channel = new Channel();
				Element ch = (Element) chn;
				String name = ch.attributeValue("id");
				channel.setName(name);
				channel.setType(ch.attributeValue("type"));

				Node swn = chn.selectSingleNode("switch");
				Element sw = (Element) swn;
				channel.setCharset(sw.attributeValue("encode"));
				channel.setExpression(sw.attributeValue("expression"));
				channel.setMode(sw.attributeValue("mode"));

				List<Node> csns = swn.selectNodes("case");
				Map<String, Service> map = new HashMap<String, Service>();
				for (Node csn : csns) {
					Element cs = (Element) csn;
					String val = cs.attributeValue("value");
					String cont = cs.getText();
					Service service = new Service();
					service.setName(cont);
					map.put(val, service);
				}
				channel.setServices(map);
				put(name, channel);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.config.IConfigLoader#writeToFile(java.lang.Object)
	 */
	@Override
	public boolean writeToFile(Channel t) {

		return false;
	}

}
