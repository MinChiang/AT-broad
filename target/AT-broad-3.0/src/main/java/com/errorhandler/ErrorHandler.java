package com.errorhandler;

import com.frame.context.DataConstant;
import com.frame.context.IContext;

/**
 * @author MinChiang
 *
 * @date 2017年3月10日
 * 
 *
 */
public class ErrorHandler {

	public static void setErrorInfo(IContext context, String errorCode, String errorMsg) {
		String string = (String) context.getData(DataConstant.ERRORCODE);
		if (string == null || "".equals(string)) {
			context.putData(DataConstant.ERRORCODE, errorCode);
		}
		string = (String) context.getData(DataConstant.ERRORMESSAGE);
		if (string == null || "".equals(string)) {
			context.putData(DataConstant.ERRORMESSAGE, errorMsg);
		}
	}

	public static String getErrorCode(IContext context) {
		return (String) context.getData(DataConstant.ERRORCODE);
	}

	public static String getErrorMessage(IContext context) {
		return (String) context.getData(DataConstant.ERRORMESSAGE);
	}

}
