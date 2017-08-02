package com.frame.error;

import com.frame.context.IContext;

/**
 * @author MinChiang
 *
 * @date 2017年3月10日
 * 
 *
 */
public interface IErrorHandler {

	void setErrorInfo(IContext context, String errorCode, String errorMsg);

}
