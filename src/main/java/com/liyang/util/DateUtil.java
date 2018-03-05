package com.liyang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Administrator
 *
 */
public class DateUtil {

	public static final SimpleDateFormat SDF_EN = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat YM = new SimpleDateFormat("yyyyMM");

	public static Date getLastMonthBegin() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		return c.getTime();
	}

	public static Date getLastMonthEnd() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		int lastMonthMaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
		return c.getTime();
	}

	public static Date getBeginTimeAccordingSign(String range) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		if ("threeday".equals(range)) {
			calendar.add(Calendar.DAY_OF_MONTH, -2);
		} else if ("week".equals(range)) {
			calendar.add(Calendar.DAY_OF_MONTH, -6);
		}
		return calendar.getTime();
	}

	/**
	 * 获取昨日23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYesterdayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取传入日期当天 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取2100年1月1日（假设终止日期）
	 * 
	 * @return
	 * @author Adam
	 */
	public static Date getLongEndTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2100, 0, 1);
		return calendar.getTime();
	}

	/**
	 * 获取2100年1月1日或当日23:59:59
	 * 
	 * @return
	 * @author Adam
	 */
	public static Date getEndTime(Date date) {
		if (null != date) {
			return getDayEnd(date);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(2100, 0, 1);
			return calendar.getTime();
		}
	}

	/**
	 * 获取2000年1月1日（假设起始时间）
	 * 
	 * @return
	 * @author Adam
	 */
	public static Date getLongBeginTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 1);
		return calendar.getTime();
	}

	/**
	 * 获取2000年1月1日或本身
	 * 
	 * @return
	 * @author Adam
	 */
	public static Date getBeginTime(Date date) {
		if (null != date) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 1);
		return calendar.getTime();
	}

	/**
	 * 验证是否是Date.toString输出的日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validateEn(String str) {
		try {
			SDF_EN.parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 将Date.toString输出的日期格式 格式化为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static String formatEnDateString(String str) throws ParseException {
		return SDF.format(SDF_EN.parse(str));
	}

	/**
	 * 传入时间格式化为 yyyyMM
	 * 
	 * @param date
	 * @return
	 */
	public static String format2YearMonthStr(Date date) {
		return YM.format(date);
	}

	/**
	 * 传入时间格式化为下月yyyyMM
	 * 
	 * @return
	 */
	public static String format2NextYearMonthStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return YM.format(calendar.getTime());
	}

	/**
	 * 传入时间 + 24h
	 * 
	 * @param date
	 * @return
	 */
	public static Date get24HLater(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	/**
	 * 获取传入时间当月的起始时间
	 */
	public static Date monthBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(2017, c.get(Calendar.MONTH), 1, 0, 0, 1);
//		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 1);
		return c.getTime();
	}
	
	/**
	 * 获取传入时间当月结束时间
	 * @return
	 * @author Adam
	 */
	public static Date monthEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, 1);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return c.getTime();
	}
	

}























