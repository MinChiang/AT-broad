package com.invoker;

import com.frame.context.DataConstant;
import com.frame.context.IContext;
import com.frame.invoker.AbstractInvoker;
import com.frame.invoker.IInvoker;
import com.frame.invokerhandler.IInvokerHandler;
import com.transfer.ITransfer;
import com.transfer.impl.ApiTransfer;
import com.util.DateUtil;
import com.util.FileUtil;
import com.util.ResourceUtil;
import com.util.XMLUtil;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import java.io.*;
import java.util.Collection;
import java.util.Properties;

/**
 * @author MinChiang
 * @create 2017-05-05-上午 11:31
 * <p>
 * 文件传输的方式：
 * 如果收到的标志位为UPLOAD，本端需要下载文件，则获取到的路径为文件在文件传输平台的路径
 * 如果收到的标志位为DOWNLOAD，本端需要上传文件，则需要传送文件上传后的路径
 */
public class FileServerInvoker extends AbstractInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileServerInvoker.class);
	//共享硬盘的文件目录地址
	private String nfsSrcPath = "/app/share/";
	//文件在本平台保存的位置
	private String fileLocPath = ResourceUtil.getResource("./files/").getPath();
	//测试文件的路径
	private String testFilePath = "test.xml";
	//文件在文件传输平台的位置
	private String apiSrcPath = "/cbs/";


	@Override
	public IInvoker invoke(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {
		String msg = (String) context.getData(DataConstant.READMESSAGE);
		String flag = getValue(msg, "FILE_ACTION");
		String tranType = getValue(msg, "TRAN_TYPE");
		File file = ResourceUtil.getResource("./message/SVR_FILE.xml");
		String content = FileUtil.read(file);
		if ("DOWNLOAD".equalsIgnoreCase(flag)) {    //如果是使用文件下载
			String filePath = null;
			if ("API".equalsIgnoreCase(tranType)) { //如果使用API方式
				filePath = apiFileUpload();
			} else if ("NFS".equalsIgnoreCase(tranType)) {  //如果使用NFS方式
				filePath = nfsFileUpload();
			}
			content = content.replace("#{FILE_PATH}", filePath);
			content = content.replace("#{FILE_ACTION}", "UPLOAD");   //生成返回报文
		} else if ("UPLOAD".equalsIgnoreCase(flag)) {   //如果是使用文件上传
			//获取文件在文件传输平台的路径
			String filePath = getValue(msg, "FILE_PATH");
			if ("API".equalsIgnoreCase(tranType)) {
				apiFileDownload(filePath);
			} else if ("NFS".equalsIgnoreCase(tranType)) {
				nfsFileDownload(filePath);
			}
			content = content.replace("#{FILE_ACTION}", "DOWNLOAD"); //生成返回报文
		}
		context.putData(DataConstant.WRITEMESSAGE, content);
		return null;
	}

	@Override
	public void before(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {

	}

	@Override
	public void after(Collection<IInvoker> invokers, IInvokerHandler handler, IContext context) {

	}

	/**
	 * 把共享磁盘某个文件拷贝到指定目录
	 *
	 * @param srcFile 文件的所在
	 */
	private void nfsFileDownload(String srcFile) {
		FileUtil.copyFile(new File(srcFile), new File(fileLocPath));
	}

	private String nfsFileUpload() {
		File file = new File(fileLocPath, testFilePath);
		FileUtil.copyFile(file, new File(nfsSrcPath));
		return new File(nfsSrcPath, file.getName()).getPath();
	}

	private void apiFileDownload(String srcFile) {
		ITransfer transfer = new ApiTransfer(ITransfer.GET);
		String fileName = new File(srcFile).getName();
		transfer.transfer(srcFile, new File(fileLocPath, fileName).getPath());
	}

	private String apiFileUpload() {
		ITransfer transfer = new ApiTransfer(ITransfer.PUT);
		String filePath = apiSrcPath + new File(fileLocPath, testFilePath).getName();
		transfer.transfer(new File(fileLocPath, testFilePath).getPath(), filePath);
		return filePath;
	}

	public static String getValue(String xml, String attributeValue) {
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(is);
			Element element = (Element) document.selectSingleNode("//data[@name='" + attributeValue + "']");
			Element sub = element.element("field");
			return sub.getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getNfsSrcPath() {
		return nfsSrcPath;
	}

	public void setNfsSrcPath(String nfsSrcPath) {
		this.nfsSrcPath = nfsSrcPath;
	}

	public String getFileLocPath() {
		return fileLocPath;
	}

	public void setFileLocPath(String fileLocPath) {
		this.fileLocPath = fileLocPath;
	}

	public String getTestFilePath() {
		return testFilePath;
	}

	public void setTestFilePath(String testFilePath) {
		this.testFilePath = testFilePath;
	}

	public String getApiSrcPath() {
		return apiSrcPath;
	}

	public void setApiSrcPath(String apiSrcPath) {
		this.apiSrcPath = apiSrcPath;
	}

}
