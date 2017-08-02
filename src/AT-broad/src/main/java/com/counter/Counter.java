package com.counter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.frame.counter.ICounter;

public class Counter implements ICounter {

	private String name;
	private AtomicLong time = new AtomicLong(0L);
	private AtomicLong count = new AtomicLong(0L);
	private AtomicBoolean status = new AtomicBoolean(true);

	public Counter(String name) {
		super();
	}

	@Override
	public long getTotalCount() {
		return count.get();
	}

	@Override
	public long getUseTime() {
		return time.get();
	}

	@Override
	public long increment() {
		return count.incrementAndGet();
	}

	@Override
	public void addTime(long time) {
		this.time.getAndAdd(time);
	}

	@Override
	public String getCounterName() {
		return name;
	}

	@Override
	public boolean isStart() {
		return status.get();
	}

	@Override
	public synchronized double getTPS() {
		return getUseTime() / getTotalCount();
	}

	@Override
	public synchronized void close() {
		status.set(false);
		reset();
	}

	public synchronized void reset() {
		time.set(0L);
		count.set(0L);
	}
}
