package com.context;

import java.util.HashMap;
import java.util.Map;

import com.frame.context.IContext;

public class Context implements IContext {

	private Map<String, Object> values;

	public Context() {
		values = new HashMap<String, Object>();
	}

	public Context(String key, String value) {
		this();
		this.putData(key, value);
	}

	public Context(Map<String, Object> map) {
		this();
		this.putData(map);
	}

	@Override
	public Object getData(String key) {
		return values.get(key);
	}

	@Override
	public void putData(String key, Object value) {
		values.put(key, value);
	}

	@Override
	public void removeData(String key) {
		values.remove(key);
	}

	@Override
	public void clear() {
		values.clear();
	}

	@Override
	public void putData(Map<String, Object> values) {
		this.values.putAll(values);
	}

}
