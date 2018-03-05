package com.liyang.util;

/**
 * 日志工具类
 * 
 * @author huanghengkun
 * @create 2017年12月12日
 */
public class Log4jUtil {

	/**
	 * 异常信息格式化
	 * 
	 * @param e
	 * @return
	 */
	public static String formatException(Exception e) {
		StringBuffer msg = new StringBuffer(e.toString() + "\n");
		for (StackTraceElement se : e.getStackTrace()) {
			msg.append("	at " + se.toString() + "\n");
		}
		return msg.toString();
	}
}
