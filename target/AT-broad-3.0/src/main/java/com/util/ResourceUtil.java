package com.util;

import java.io.File;

/**
 * @author MinChiang
 * @date 2017年2月24日
 */
public class ResourceUtil {

	private static final String BASEPATH = "user.dir";

	public static File getResource(String path) {
		File file = new File(System.getProperty(BASEPATH), path);
		return file.exists() ? file : null;
	}

	public static String getResourceAbsPath(String path) {
		File file = new File(System.getProperty(BASEPATH), path);
		return file.exists() ? file.getAbsolutePath() : "";
	}

}
