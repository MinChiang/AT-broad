package com.invokerhandler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.InvokerBean;
import com.bean.InvokerHandlerBean;
import com.configloader.InvokerConfigLoader;
import com.configloader.InvokerHandlerConfigLoader;
import com.configloadermanager.ConfigLoaderManager;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.frame.invokerhandler.IInvokerHandlerFactory;
import com.util.ReflectUtil;

/**
 * 本类是一个提供产生处理控制器的工厂，负责配置默认的处理控制器，产生并向执行控制器中注入读取、查找报文、写入执行器
 * 
 * @author MinChiang
 *
 */
public class InvokerHandlerFactory implements IInvokerHandlerFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvokerHandlerFactory.class);
	// private static InvokerHandlerFactory invokerHandlerFactory;
	private static InvokerHandlerConfigLoader invokerHandlerConfigLoader = null;
	private static Map<String, IInvokerHandler> invokerHandlers = new HashMap<String, IInvokerHandler>();

	private InvokerHandlerFactory() {
		super();
		load();
	}

	public static InvokerHandlerFactory getInstance() {
		// if (invokerHandlerFactory == null) {
		// invokerHandlerFactory = new InvokerHandlerFactory();
		// }
		// return invokerHandlerFactory;
		return InvokerHandlerFactoryHolder.invokerHandlerFactory;
	}

	private void load() {
		invokerHandlerConfigLoader = (InvokerHandlerConfigLoader) ConfigLoaderManager.getInstance()
				.getConfigLoader("invokerHandlerConfigLoader");
		for (InvokerHandlerBean invokerHandlerBean : invokerHandlerConfigLoader.toList()) {
			IInvokerHandler invokerHandler = new BaseInvokerHandler();
			invokerHandler.setInvokerHandlerName(invokerHandlerBean.getInvokerHandlerName());
			for (InvokerBean invokerBean : invokerHandlerBean.getInvokers()) {
				try {
					IInvoker invoker = null;
					String name = invokerBean.getInvokerName();
					if (name != null && !"".equals(name)) { // 如果invoker的名称不为空，那么直接装载已经配置的对象即可
						InvokerConfigLoader invokerConfigLoader = (InvokerConfigLoader) ConfigLoaderManager
								.getInstance().getConfigLoader("invokerConfigLoader");
						IInvoker inv = invokerConfigLoader.get(name);
						if (inv != null) {
							invoker = inv;
						} else {
							if (LOGGER.isErrorEnabled()) {
								LOGGER.error("执行器{}不存在，请在invoker.xml中进行配置！", inv);
							}
						}
					} else { // 如果invoker的名称为空，那么需要通过反射进行invoker的创建
						invoker = ReflectUtil.newInstance(Class.forName(invokerBean.getInvokerClass()), null, null);
						for (Entry<String, String> entry : invokerBean.getParams().entrySet()) {
							String key = entry.getKey();
							String value = entry.getValue();
							if (key != null && !"".equals(key) && value != null && !"".equals(value)) {
								ReflectUtil.setParamAutoFixBaseType(invoker, key, value);
							}
						}
					}
					invokerHandler.regist(invoker);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			invokerHandlers.put(invokerHandlerBean.getInvokerHandlerName(), invokerHandler);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frame.invokerhandler.AbstractInvokerHandlerFactory#getInvokerHandler(
	 * java.lang.String)
	 */
	@Override
	public IInvokerHandler getInvokerHandler(String invokerHandlerName) {
		return invokerHandlers.get(invokerHandlerName);
	}

	private static class InvokerHandlerFactoryHolder {
		private static InvokerHandlerFactory invokerHandlerFactory = new InvokerHandlerFactory();
	}

	public static void main(String[] args) {
		try {
			ConfigLoaderManager.getInstance().batchLoad();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		IInvokerHandler iInvokerHandler = InvokerHandlerFactory.getInstance().getInvokerHandler("default");
		for (IInvoker iInvoker : iInvokerHandler.getInvokers()) {
			System.out.println(iInvoker);
		}
	}

}
