package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author MinChiang
 * @date 2017年3月26日
 */
public class DateUtil {

	public static String getSimpleFormatDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return simpleDateFormat.format(new Date());
	}

}
