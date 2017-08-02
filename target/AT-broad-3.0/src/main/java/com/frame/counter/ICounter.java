package com.frame.counter;

public interface ICounter {
	
	long getTotalCount();

	long getUseTime();

	double getTPS();

	long increment();

	void addTime(long time);

	String getCounterName();

	boolean isStart();

	void close();

	void reset();
	
}
