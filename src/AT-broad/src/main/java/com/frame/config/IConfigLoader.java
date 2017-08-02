package com.frame.config;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 配置类
 * 
 * @author MinChiang
 *
 * @date 2017年1月23日
 * 
 * @param <K>
 *            唯一识别配置的参数类
 * @param <T>
 *            管理类型
 *
 */
public interface IConfigLoader<K, T> {

	void load(String configPath) throws FileNotFoundException;

	void reload(String configPath) throws FileNotFoundException;

	void load() throws FileNotFoundException;

	void reload() throws FileNotFoundException;

	T get(K k);

	void put(K k, T t);

	List<T> toList();

	Map<K, T> getContext();

	boolean writeToFile(T t);

}
