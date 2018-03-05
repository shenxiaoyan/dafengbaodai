package com.liyang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liyang.helper.IDCardFormatException;

/**
 * 校验身份证号码的工具类
 * 
 * @author duyiting
 * @date 2017年12月7日
 * @Version 1.0
 */
public class IDCardUtil {

	private final static Pattern IDCARDPATTERN = Pattern.compile("[0-9]*(X)?");

	/**
	 * 获取身份证信息中的出生年月
	 * @param idCard
	 * @return
	 * @throws IDCardFormatException
	 */
	public static Date getBirthByIdCard(String idCard) throws IDCardFormatException {
		if (!iDVallidate(idCard)) {
			throw new IDCardFormatException();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(idCard.substring(6, 14));
		} catch (ParseException e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据身份编号获取性别 male：true female:false
	 * 
	 * @param idCard
	 * @return
	 * @throws IDCardFormatException
	 */
	public static Boolean isMale(String idCard) throws IDCardFormatException {
		if (!iDVallidate(idCard)) {
			throw new IDCardFormatException();
		}
		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean iDVallidate(String idCard) {
		boolean flag = false;
		boolean area = ValidateUtil.isValidAreaCode(idCard);
		boolean numberOrLetterX = ValidateUtil.isNumberOrLetterX(idCard);
		boolean dateOfBirth = ValidateUtil.isValidDateOfBirth(idCard);
		if (area && numberOrLetterX && dateOfBirth) {
			flag = true;
		}
		return flag;

	}

	public static class ValidateUtil {
		/**
		 * 功能：设置地区编码
		 * 
		 * @return Map<String,Object> 对象
		 */
		private static Map<String, Object> getAreaCode() {
			Map<String, Object> areaCodeMap = new HashMap<String, Object>();
			areaCodeMap.put("11", "北京");
			areaCodeMap.put("12", "天津");
			areaCodeMap.put("13", "河北");
			areaCodeMap.put("14", "山西");
			areaCodeMap.put("15", "内蒙古");
			areaCodeMap.put("21", "辽宁");
			areaCodeMap.put("22", "吉林");
			areaCodeMap.put("23", "黑龙江");
			areaCodeMap.put("31", "上海");
			areaCodeMap.put("32", "江苏");
			areaCodeMap.put("33", "浙江");
			areaCodeMap.put("34", "安徽");
			areaCodeMap.put("35", "福建");
			areaCodeMap.put("36", "江西");
			areaCodeMap.put("37", "山东");
			areaCodeMap.put("41", "河南");
			areaCodeMap.put("42", "湖北");
			areaCodeMap.put("43", "湖南");
			areaCodeMap.put("44", "广东");
			areaCodeMap.put("45", "广西");
			areaCodeMap.put("46", "海南");
			areaCodeMap.put("50", "重庆");
			areaCodeMap.put("51", "四川");
			areaCodeMap.put("52", "贵州");
			areaCodeMap.put("53", "云南");
			areaCodeMap.put("54", "西藏");
			areaCodeMap.put("61", "陕西");
			areaCodeMap.put("62", "甘肃");
			areaCodeMap.put("63", "青海");
			areaCodeMap.put("64", "宁夏");
			areaCodeMap.put("65", "新疆");
			areaCodeMap.put("71", "台湾");
			areaCodeMap.put("81", "香港");
			areaCodeMap.put("82", "澳门");
			areaCodeMap.put("91", "国外");
			return areaCodeMap;
		}

		/**
		 * 功能:校验地区码是否合法
		 * 
		 * @param idCard
		 *            身份证号码
		 * @return true:合法,false:非法
		 */
		public static boolean isValidAreaCode(String idCard) {
			String areaCode = idCard.substring(0, 2);
			Map<String, Object> map = getAreaCode();
			boolean flag = map.containsKey(areaCode);
			return flag;
		}

		/**
		 * @param idCard
		 *            身份证号码
		 * @return true:数字,false:含有非数字
		 */
		public static boolean isNumberOrLetterX(String idCard) {
			boolean flag = false;
			Matcher m = IDCARDPATTERN.matcher(idCard);
			if (m.matches()) {
				flag = true;
			}
			return flag;
		}

		/**
		 * 功能:校验身份证号码的出生年-月-日是否合法
		 * 
		 * @param idCard
		 *            身份证号码
		 * @return 合法与否
		 */
		public static boolean isValidDateOfBirth(String idCard) {
			Boolean flag = true;
			int year = Integer.parseInt(idCard.substring(6, 10));
			int month = Integer.parseInt(idCard.substring(10, 12));
			int day = Integer.parseInt(idCard.substring(12, 14));

			// 年份的话,仅仅设定区间为向前200年
			Date date = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("YYYY");
			String formatDate = simple.format(date);
			int nowYear = Integer.parseInt(formatDate);
			if (year < nowYear - 200 || year > nowYear) {
				flag = false;
				System.out.println("年份" + year + "不在【" + (nowYear - 200) + "," + nowYear + "】之间");
			}
			if (month > 12 || month < 1) {
				flag = false;
				System.out.println("月份" + month + "不在【01-12】之间");
			}
			if (day > 31 || day < 1) {
				flag = false;
				System.out.println("日" + day + "不在【01-31】之间");
			}
			return flag;
		}
	}

}
