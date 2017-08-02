package com.util;

import java.io.*;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil {

	private Document doc;

	public XMLUtil(File file) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		doc = saxReader.read(file);
	}

	public XMLUtil(String xml) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		doc = saxReader.read(new StringReader(xml));
	}

	/**
	 * 根据父节点、节点名称和节点对应的属性获取对应的节点集合
	 *
	 * @param parent    父节点
	 * @param nodeName  节点名称
	 * @param attrName  节点属性名称
	 * @param attrValue 对应的属性内容
	 * @return 符合对应规则的节点集合
	 */
	@SuppressWarnings("unchecked")
	public static List<Node> getNodes(Node parent, String nodeName, String attrName, String attrValue) {
		StringBuffer sb = new StringBuffer(".//");
		if (nodeName != null && !"".equals(nodeName)) {
			sb.append(nodeName);
			if (attrName != null && !"".equals(attrName) && attrValue != null && "".equals(attrValue)) {
				sb.append("[@" + attrName + "='" + attrValue + "']");
			}
		}
		return parent.selectNodes(sb.toString());
	}

	/**
	 * 根据父节点和节点名称获取节点集合
	 *
	 * @param parent   父节点
	 * @param nodeName 节点名称
	 * @return 符合对应规则的节点集合
	 */
	public static List<Node> getNodes(Node parent, String nodeName) {
		return getNodes(parent, nodeName, null, null);
	}

	/**
	 * 根据父节点、节点名称和节点对应的属性获取对应的唯一节点
	 *
	 * @param parent    父节点
	 * @param nodeName  节点名称
	 * @param attrName  节点属性名称
	 * @param attrValue 对应的属性内容
	 * @return 符合对应规则的唯一节点
	 */
	public static Node getNode(Node parent, String nodeName, String attrName, String attrValue) {
		StringBuffer sb = new StringBuffer(".//");
		if (nodeName != null && !"".equals(nodeName)) {
			sb.append(nodeName);
			if (attrName != null && !"".equals(attrName) && attrValue != null && !"".equals(attrValue)) {
				sb.append("[@" + attrName + "='" + attrValue + "']");
			}
		}
		return parent.selectSingleNode(sb.toString());
	}

	/**
	 * 根据父节点和节点名称获取唯一节点
	 *
	 * @param parent   父节点
	 * @param nodeName 节点名称
	 * @return 符合对应规则的唯一节点
	 */
	public static Node getNode(Node parent, String nodeName) {
		return getNode(parent, nodeName, null, null);
	}

	/**
	 * 根据当前已经解析的XML文档、节点名称和节点对应的属性获取对应的节点集合
	 *
	 * @param nodeName  节点名称
	 * @param attrName  节点属性名称
	 * @param attrValue 对应的属性内容
	 * @return 符合对应规则的节点集合
	 */
	public List<Node> getDocNodes(String nodeName, String attrName, String attrValue) {
		return getNodes(doc, nodeName, attrName, attrValue);
	}

	/**
	 * 根据当前已经解析的XML文档和节点名称获取对应的节点集合
	 *
	 * @param nodeName 节点名称
	 * @return 符合对应规则的节点集合
	 */
	public List<Node> getDocNodes(String nodeName) {
		return getNodes(doc, nodeName);
	}

	/**
	 * 根据当前已经解析的XML文档、节点名称和节点对应的属性获取对应的唯一节点
	 *
	 * @param nodeName  节点名称
	 * @param attrName  节点属性名称
	 * @param attrValue 对应的属性内容
	 * @return 符合对应规则的唯一节点
	 */
	public Node getDocNode(String nodeName, String attrName, String attrValue) {
		return getNode(doc, nodeName, attrName, attrValue);
	}

	/**
	 * 根据当前已经解析的XML文档和节点名称获取唯一节点
	 *
	 * @param nodeName 节点名称
	 * @return 符合对应规则的唯一节点
	 */
	// public Node getDocNode(String nodeName) {
	// return getDocNode(nodeName, null, null);
	// }
	public Node getDocNode(String nodeName) {
		return doc.selectSingleNode(nodeName);
	}

	/**
	 * 根据父几点和节点对应的值获取单一节点
	 *
	 * @param parent   父节点
	 * @param nodeName 节点名称
	 * @param value    对应的节点值
	 * @return 符合对应规则的唯一节点
	 */
	public Node getEqual(Node parent, String nodeName, String value) {
		List<Node> list = getNodes(parent, nodeName);
		Node node = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				node = list.get(i);
				if (node.getName().equals(nodeName) && node.getText().equals(value)) {
					return node;
				}
			}
		}
		return null;
	}

	/**
	 * 根据XML文档写出对应的XML
	 *
	 * @param doc XML文档
	 * @return XML字符串
	 */
	public static String writeXML(Document doc) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setIndentSize(4);
		XMLWriter writer = null;
		StringWriter sw = new StringWriter();
		try {
			writer = new XMLWriter(sw, format);
			writer.write(doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sw.toString();
	}

	/**
	 * 根据XML文档向文件中写入XML内容
	 *
	 * @param fileName 要写入的文件
	 * @param doc      XML文档
	 */
	public static void writeXML(String fileName, Document doc) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setIndentSize(4);
		File file = new File(fileName);
		XMLWriter writer = null;
		try {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new XMLWriter(new FileOutputStream(file), format);
			writer.write(doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeXML(String fileName) {
		writeXML(fileName, doc);
	}

	/**
	 * 根据默认的风格格式化对应的xml文档
	 * 默认风格：UTF-8编码，前4个空格缩进
	 *
	 * @param xml 需要格式化的xml
	 * @return 格式化后的xml
	 */
	public static String format(String xml) {
		SAXReader saxReader = new SAXReader();
		String result = null;
		StringWriter writer = null;
		try {
			Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes()));
			if (document == null) {
				return null;
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			format.setIndentSize(4);
			writer = new StringWriter();
			XMLWriter xmlwriter = new XMLWriter(writer, format);
			xmlwriter.write(document);
			result = writer.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}