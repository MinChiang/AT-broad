package com.frame.context;

import java.util.Map;

public interface IContext {

	Object getData(String key);

	void putData(String key, Object value);

	void removeData(String key);

	void clear();

	void putData(Map<String, Object> map);
	
}
