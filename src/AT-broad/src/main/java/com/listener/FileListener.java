package com.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.listener.IObserver;

/**
 * @author MinChiang
 *
 * @date 2017年3月18日
 * 
 *
 */
public class FileListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileListener.class);
	private static final long DEFAULTLOOPTIME = 3000L;

	private static List<FileSubject> fileSubjects;
	private static FileListener fileListener;
	private AtomicBoolean atomicBoolean;

	/**
	 * 
	 */
	public FileListener() {
		fileSubjects = new ArrayList<FileSubject>();
	}

	public static final FileListener getInstance() {
		if (fileListener == null) {
			fileListener = new FileListener();
		}
		return fileListener;
	}

	public void regist(File listenFile, IObserver observer) {
		for (FileSubject fs : fileSubjects) {
			if (fs.getListenFile().getAbsolutePath().equalsIgnoreCase(listenFile.getAbsolutePath())) {
				fs.registObserver(observer);
				return;
			}
		}
		FileSubject fileSubject = new FileSubject(listenFile);
		fileSubject.registObserver(observer);
		fileSubjects.add(fileSubject);
	}

	public void start() {
		atomicBoolean = new AtomicBoolean(true);
	}

	public void stop() {
		atomicBoolean.set(false);
	}

	public void startListen() {
		new Thread() {
			@Override
			public void run() {
				while (atomicBoolean.get()) {
					for (FileSubject fileSubject : fileSubjects) {
						fileSubject.judgeModified();
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("检索{}文件更新状态", fileSubject.getListenFile().getAbsolutePath());
						}
					}
					try {
						Thread.sleep(DEFAULTLOOPTIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
