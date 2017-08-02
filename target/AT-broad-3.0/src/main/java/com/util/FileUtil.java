package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class FileUtil {

	public static String read(File file, String encoding) {
		if (file == null || file.isDirectory() || !file.exists()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, encoding);
			char[] chars = new char[1024];
			int len = 0;
			while ((len = isr.read(chars)) != -1) {
				sb.append(chars, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				isr.close();
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String read(File file) {
		return read(file, "UTF-8");
	}

	public static void write(File file, String str, String encoding) {
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, encoding);
			osw.write(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				osw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void write(File file, String str) {
		write(file, str, "UTF-8");
	}

	public static boolean copyContent(File src, File dest, String encoding) {
		if (src != null && src.exists() && src.isFile()) {
			String str = read(src, encoding);
			write(dest, str, encoding);
			return true;
		}
		return false;
	}

	public static boolean copyContent(File src, File dest) {
		return copyContent(src, dest, Charset.defaultCharset().toString());
	}

	public static void copyFile(File srcFile, File destFilePath) {
		if (!srcFile.exists()) {
			return;
		}
		if (!destFilePath.exists()) {
			destFilePath.mkdirs();
		}
		File destFile = new File(destFilePath, srcFile.getName());
		try {
			if (srcFile.isFile()) {
				destFile.createNewFile();
				copyContent(srcFile, destFile);
			} else {
				destFile.mkdirs();
				for (File f : srcFile.listFiles()) {
					copyFile(f, destFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteFiles(File file) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				f.delete();
			} else {
				deleteFiles(f);
			}
		}
		file.delete();
	}
	
	public static void main(String[] args) {
		String str = FileUtil.read(ResourceUtil.getResource("./message/123456.xml"));
		System.out.println(str);
		
	}
}
