package com.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author MinChiang
 *
 * @date 2017年3月13日
 * 
 *
 */
public class XML2ObjectUtil<T> {

	private static final Map<String, Class<?>> BASETYPE = new HashMap<String, Class<?>>();
	private Document doc;

	static {
		BASETYPE.put("string", String.class);
		BASETYPE.put("double", double.class);
		BASETYPE.put("fload", float.class);
		BASETYPE.put("int", int.class);
		BASETYPE.put("long", long.class);
		BASETYPE.put("short", short.class);
		BASETYPE.put("boolean", boolean.class);
		BASETYPE.put("char", char.class);
		BASETYPE.put("char[]", char[].class);
	}

	public XML2ObjectUtil(File file) {
		load(file);
	}

	public void load(File file) {
		if (file != null && file.exists()) {
			SAXReader saxReader = new SAXReader();
			try {
				this.doc = saxReader.read(file);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, T> parse() throws ClassNotFoundException {
		Element root = doc.getRootElement();
		Element e = null;
		Map<String, T> map = new HashMap<String, T>();
		Iterator<Element> iterator = root.elementIterator();
		while (iterator.hasNext()) {
			e = iterator.next();
			String name = e.attributeValue("name");
			// String value = e.attributeValue("value");
			String cls = e.attributeValue("class");
			Class<?> clz = null;
			if (name == null || "".equals(name) || cls == null || "".equals(cls)) {
				continue;
			} else {
				clz = Class.forName(cls);
			}
			T objVal = ReflectUtil.newInstance(clz);
			parse(e, objVal);
			map.put(name, objVal);
		}
		return map;
	}

	public static boolean isBaseType(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		if ("string".equals(value)) {
			return true;
		} else if ("int".equals(value)) {
			return true;
		} else if ("double".equals(value)) {
			return true;
		} else if ("long".equals(value)) {
			return true;
		} else if ("short".equals(value)) {
			return true;
		} else if ("boolean".equals(value)) {
			return true;
		} else if ("float".equals(value)) {
			return true;
		} else if ("char".equals(value)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void parse(Element root, Object objParent) throws ClassNotFoundException {
		Element e = null;
		Iterator<Element> iterator = root.elementIterator();
		while (iterator.hasNext()) {
			e = iterator.next();
			String name = e.attributeValue("name");
			String value = e.attributeValue("value");
			String cls = e.attributeValue("class", "string");
			Class<?> clz = null;
			if (name == null || "".equals(name)) {
				continue;
			}

			Object objVal = null;
			// 如果没有设定值，则还不是末端对象
			if (value == null) {
				objVal = ReflectUtil.newInstance(Class.forName(cls));
				parse(e, objVal);
			} else {
				// 如果是基本数据类型，需要特殊处理
				if (isBaseType(cls)) {
					// clz = Class.forName("float.class");
					clz = BASETYPE.get(cls);
					objVal = ReflectUtil.string2BaseType(value, clz);
					// 基本类型使用field反射进行参数设定
					ReflectUtil.setParamByField(objParent, name, clz, objVal);
				} else {
					// clz = Class.forName(cls);
					// 生成参数对象（无参构造方法）
					// objVal = ReflectUtil.newInstance(clz);
					// 非基本类型使用setMethod进行参数设定
					// ReflectUtil.setParamByMethod(objParent, name, clz,
					// objVal);
					ReflectUtil.setParamByMethod(objParent, name, String.class, value);
				}

			}
		}
	}
}
