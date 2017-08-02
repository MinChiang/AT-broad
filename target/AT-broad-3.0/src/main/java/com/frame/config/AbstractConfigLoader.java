package com.frame.config;

import com.util.ResourceUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractConfigLoader<K, T> implements IConfigLoader<K, T> {

	protected String configPath;
	private Map<K, T> ts;

	public AbstractConfigLoader(String configPath) {
		super();
		this.configPath = configPath;
		this.ts = new ConcurrentHashMap<K, T>();
	}

	@Override
	public T get(K k) {
		return ts.get(k);
	}

	@Override
	public void put(K k, T t) {
		ts.put(k, t);
	}

	@Override
	public void reload(String configPath) throws FileNotFoundException {
		load(configPath);
	}

	@Override
	public void load(String configPath) throws FileNotFoundException {
		File file = ResourceUtil.getResource(configPath);
		if (!file.exists()) {
			throw new FileNotFoundException("配置文件：" + configPath + "读取失败");
		}
		read(file);
	}

	@Override
	public void load() throws FileNotFoundException {
		load(this.configPath);
	}

	@Override
	public void reload() throws FileNotFoundException {
		reload(this.configPath);
	}

	@Override
	public Map<K, T> getContext() {
		return this.ts;
	}

	@Override
	public List<T> toList() {
		List<T> list = new ArrayList<T>();
		for (K k : ts.keySet()) {
			list.add(ts.get(k));
		}
		return list;
	}

	public abstract void read(File file);
}
