package com.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.bean.Service;
import com.util.XMLUtil;

/**
 * 渠道管理器
 * 
 * @author MinChiang
 *
 */
public class ChannelManager {

	public static final String DEFAULTXPATH = "channels/channel";
	public static final String LOADFILE = "./serviceIdentify/serviceIdentify.xml";
	private static ChannelManager channelManager;
	private static Map<String, Channel> channels;

	private ChannelManager() throws FileNotFoundException {
		load();
	}

	public static ChannelManager getInstance() throws FileNotFoundException {
		if (channelManager == null) {
			channelManager = new ChannelManager();
		}
		return channelManager;
	}

	@SuppressWarnings("unchecked")
	private void load() throws FileNotFoundException {
		File file = new File(LOADFILE);
		if (!file.exists()) {
			throw new FileNotFoundException(LOADFILE + "文件不存在");
		}
		try {
			XMLUtil xmlUtil = new XMLUtil(file);
			channels = new HashMap<String, Channel>();
			List<Node> nodes = xmlUtil.getDocNodes(DEFAULTXPATH);
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
				channels.put(name, channel);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void reload() throws FileNotFoundException {
		load();
	}

}
