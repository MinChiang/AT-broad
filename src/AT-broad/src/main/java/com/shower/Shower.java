package com.shower;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.frame.counter.ICounter;
import com.frame.shower.IShower;

/**
 * 本类提供了简单数据打印的功能对象，可以设置打印的时间和打印的ICounter对象
 * 
 * @author MinChiang
 *
 */
public class Shower implements IShower {

	private long interval = 1000L;
	private static boolean status = false;
	private static Map<String, ICounter> counters;

	public Shower() {
		counters = new ConcurrentHashMap<String, ICounter>();
		status = true;
	}

	@Override
	public void show() {
		long time;
		ICounter counter = null;
		for (String str : counters.keySet()) {
			counter = counters.get(str);
			time = counter.getTotalCount();
			System.out.println(counter.getCounterName() + "执行次数：  " + time + "\tTPS：  " + time / (interval * 1000));
		}
	}

	@Override
	public void cancel() {
		status = false;
	}

	@Override
	public void setInterval(long interval) {
		this.interval = interval;
	}

	@Override
	public void run() {
		while (status) {
			try {
				// 睡眠interval毫秒
				Thread.sleep(interval);
				// 打印
				show();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void regist(ICounter counter) {
		counters.put(counter.getCounterName(), counter);
	}

	@Override
	public void remove(ICounter counter) {
		counters.remove(counter.getCounterName());
	}
}
