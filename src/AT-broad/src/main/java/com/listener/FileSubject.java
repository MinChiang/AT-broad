package com.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.listener.IObserver;
import com.frame.listener.ISubject;

/**
 * @author MinChiang
 *
 * @date 2017年3月18日
 * 
 *
 */
public class FileSubject implements ISubject {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileSubject.class);

	private List<IObserver> observers;
	private File listenFile;
	private long lastModifiedTime;

	public FileSubject(File listenFile) {
		super();
		this.listenFile = listenFile;
		lastModifiedTime = listenFile.lastModified();
		observers = new ArrayList<IObserver>();
	}

	public File getListenFile() {
		return listenFile;
	}

	public void setListenFile(File listenFile) {
		this.listenFile = listenFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frame.listener.ISubject#registObserver(com.frame.listener.IObserver)
	 */
	@Override
	public void registObserver(IObserver observer) {
		observers.add(observer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frame.listener.ISubject#removeObserver(com.frame.listener.IObserver)
	 */
	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frame.listener.ISubject#notifyObservers()
	 */
	@Override
	public void notifyObservers() {
		for (IObserver observer : observers) {
			observer.update();
		}
	}

	public void judgeModified() {
		if (listenFile != null && listenFile.exists()) {
			if (lastModifiedTime != listenFile.lastModified()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("{}文件进行更新，更新时间为：{}", listenFile.getAbsolutePath(), lastModifiedTime);
				}
				notifyObservers();
			}
		}
	}

}
