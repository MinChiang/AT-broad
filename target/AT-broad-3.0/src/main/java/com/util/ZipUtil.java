package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static final int DEFAULTREADLEN = 2048;

	private int readLen = DEFAULTREADLEN;
	private boolean isReplace = true;

	public ZipUtil(int readLen, boolean isReplace) {
		this.readLen = readLen;
		this.isReplace = isReplace;
	}

	public ZipUtil(int readLen) {
		this(readLen, true);
	}

	public ZipUtil() {
		super();
	}

	public int getReadLen() {
		return readLen;
	}

	public void setReadLen(int readLen) {
		this.readLen = readLen;
	}

	public boolean isReplace() {
		return isReplace;
	}

	public void setReplace(boolean isReplace) {
		this.isReplace = isReplace;
	}

	public void zip(String zipFileName, File inputFile) {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			zip(out, inputFile, inputFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void zip(ZipOutputStream out, File f, String base) {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			// base = base.length() == 0 ? "" : base + "/";
			base += "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			FileInputStream in = null;
			try {
				out.putNextEntry(new ZipEntry(base));
				in = new FileInputStream(f);
				int len;
				byte[] bytes = new byte[readLen];
				while ((len = in.read(bytes)) != -1) {
					out.write(bytes, 0, len);
				}
				out.closeEntry();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void unzip(String fileName, File unzipFile) {
		ZipInputStream zis = null;
		FileOutputStream fos = null;
		try {
			zis = new ZipInputStream(new FileInputStream(unzipFile));
			ZipEntry entry = null;
			byte[] bytes = new byte[readLen];
			int len;
			while ((entry = zis.getNextEntry()) != null) {
				File file = new File(fileName, entry.getName());
				if (!file.exists()) {
					File parentFile = file.getParentFile();
					if (!parentFile.exists()) {
						parentFile.mkdirs();
					}
					file.createNewFile();
				} else {
					if (!isReplace) {
						continue;
					}
				}
				fos = new FileOutputStream(file);
				while ((len = zis.read(bytes)) != -1) {
					fos.write(bytes, 0, len);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zis != null) {
					zis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
