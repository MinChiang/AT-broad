package com.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 系统配置器
 * 
 * @author MinChiang
 *
 * @date 2017年1月23日
 * 
 *
 */

@XmlRootElement(name = "system")
public class SystemConfig {

	// 针对于threadPolicy参数配置
	public static final String FIX = "fix";
	public static final String CACHED = "cached";

	/**
	 * 系统名称
	 */
	private String systemName;

	/**
	 * 开放端口号
	 */
	private int port;

	/**
	 * 报文配置策略：static返回单一报文，dynamic返回的报文随着交易号的改变而改变
	 */
	private String msgpolicy;

	/**
	 * 读取长度头长度
	 */
	private int readLen;

	/**
	 * 写出长度头长度
	 */
	private int writeLen;

	/**
	 * 线程池配置策略：fix使用固定大小线程池，cache使用动态线程池
	 */
	private String threadpolicy;

	/**
	 * 分配最大的线程数目
	 */
	private int threadNum;

	/**
	 * 读取编码
	 */
	private String readEncoding;

	/**
	 * 写出编码
	 */
	private String writeEncoding;

	/**
	 * 动态报文的交易识别码，仅针对动态报文有效
	 */
	private String tradeNoPath;

	/**
	 * 系统服务器的实现类，必须实现IServerOrClient
	 */
	private String impClass;

	/**
	 * 适配流程设定
	 */
	private String invokerHandler;

	/**
	 * 上下文路径
	 */
	private String context;

	public SystemConfig() {
		super();
	}

	public String getSystemName() {
		return systemName;
	}

	@XmlAttribute(name = "name")
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public int getPort() {
		return port;
	}

	@XmlElement
	public void setPort(int port) {
		this.port = port;
	}

	public String getThreadpolicy() {
		return threadpolicy;
	}

	@XmlElement
	public void setThreadpolicy(String threadpolicy) {
		if (threadpolicy == null || "".equals(threadpolicy)) {
			this.threadpolicy = FIX;
		} else {
			this.threadpolicy = threadpolicy;
		}
	}

	public int getReadLen() {
		return readLen;
	}

	@XmlElement
	public void setReadLen(int readLen) {
		this.readLen = readLen;
	}

	public int getWriteLen() {
		return writeLen;
	}

	@XmlElement
	public void setWriteLen(int writeLen) {
		this.writeLen = writeLen;
	}

	public int getThreadNum() {
		return threadNum;
	}

	@XmlElement
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public String getReadEncoding() {
		return readEncoding;
	}

	@XmlElement
	public void setReadEncoding(String readEncoding) {
		if (readEncoding == null || "".equals(readEncoding)) {
			readEncoding = "UTF-8";
		} else {
			this.readEncoding = readEncoding;
		}
	}

	public String getWriteEncoding() {
		return writeEncoding;
	}

	@XmlElement
	public void setWriteEncoding(String writeEncoding) {
		if (writeEncoding == null || "".equals(writeEncoding)) {
			writeEncoding = "UTF-8";
		} else {
			this.writeEncoding = writeEncoding;
		}
	}

	public String getTradeNoPath() {
		return tradeNoPath;
	}

	@XmlElement
	public void setTradeNoPath(String tradeNoPath) {
		this.tradeNoPath = tradeNoPath;
	}

	public String getImpClass() {
		return impClass;
	}

	@XmlElement
	public void setImpClass(String impClass) {
		this.impClass = impClass;
	}

	public String getInvokerHandler() {
		return invokerHandler;
	}

	@XmlElement
	public void setInvokerHandler(String invokerHandler) {
		this.invokerHandler = invokerHandler;
	}

	public String getContext() {
		return context;
	}

	@XmlElement
	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "SystemConfig [systemName=" + systemName + ", port=" + port + ", msgpolicy=" + msgpolicy + ", readLen="
				+ readLen + ", writeLen=" + writeLen + ", threadpolicy=" + threadpolicy + ", threadNum=" + threadNum
				+ ", readEncoding=" + readEncoding + ", writeEncoding=" + writeEncoding + ", tradeNoPath=" + tradeNoPath
				+ ", impClass=" + impClass + ", invokerHandler=" + invokerHandler + ", context=" + context + "]";
	}

}
