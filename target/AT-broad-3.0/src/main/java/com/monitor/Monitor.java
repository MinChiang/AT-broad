package com.monitor;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

import com.frame.monitor.IMonitor;
import com.frame.paramhandler.IParamHandler;

/**
 * 本类提供一个简单的用户输入监视处理器，当接收到数据时，处理器会调用相对的handller进行输入数据信息处理
 * 
 * @author MinChiang
 *
 */
public class Monitor implements IMonitor {
	// 处理分割正则表达式
	private static final String SPLITREGEX = "\\s+";

	private static final boolean RUN = true;
	private static final boolean STOP = false;
	private static Monitor monitor;
	private static Vector<IParamHandler> handlers;
	private static boolean status = false;

	// 简单控制台输入
	private Scanner scanner;
	private InputStream is;

	public static final Monitor getInstance(InputStream is) {
		if (monitor == null) {
			if (is == null) {
				monitor = new Monitor(System.in);
			} else {
				monitor = new Monitor(is);
			}
		}
		return monitor;
	}

	/**
	 * 
	 * @param is
	 *            输入流
	 */
	private Monitor(InputStream is) {
		this.is = is;
		scanner = new Scanner(is);
		handlers = new Vector<IParamHandler>();
	}

	@Override
	public String input() {
		return scanner.nextLine();
	}

	@Override
	public void register(IParamHandler handler) {
		handlers.add(handler);
	}

	@Override
	public String[] handle(String arg) {
		return arg.split(SPLITREGEX);
	}

	@Override
	public void run() {
		status = RUN;
		while (status == RUN) {
			String[] args = handle(input());
			String flag = args[0];
			IParamHandler handler = null;
			if (flag != null && !"".equals(flag)) {
				for (IParamHandler tmp : handlers) {
					String[] params = tmp.getHandleFlag();
					for (String param : params) {
						if (flag.equalsIgnoreCase(param)) {
							handler = tmp;
							break;
						}
					}
				}
				if (handler == null) {
					System.out.println("无效输入");
				} else {
					handler.handle(args);
				}
			}
		}
	}

	@Override
	public void cancel() {
		scanner.close();
		status = STOP;
	}

	public void start() {
		scanner = new Scanner(is);
		status = RUN;
	}
}
