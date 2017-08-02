package com.frame.listener;

import java.io.File;

/**
 * @author MinChiang
 *
 * @date 2017年3月18日
 * 
 *
 */
public interface IFileObserver extends IObserver {

	File getListenFile();
	
}
