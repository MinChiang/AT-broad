package com.frame.listener;

/**
 * @author MinChiang
 *
 * @date 2017年3月18日
 * 
 *
 */
public interface ISubject {

	void registObserver(IObserver observer);

	void removeObserver(IObserver observer);

	void notifyObservers();

}
