package com.frame.shower;

import com.frame.counter.ICounter;

public interface IShower extends Runnable {
	void show();

	void cancel();

	void setInterval(long interval);

	void regist(ICounter counter);

	void remove(ICounter counter);
}
