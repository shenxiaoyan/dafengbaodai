package com.liyang.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
* 类说明   
* @author lcj 
* @date 2017年10月25日  新建  
*/
public class PatternUtil {
	/**
	 * 手机号正则
	 */
	public final static Pattern MOBILEPHONE_PATTERN = Pattern
			.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$");

	/**
	 * 固定电话正则
	 */
	public final static Pattern TELEPHONE_PATTERN = Pattern.compile("^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})$");
	
	public static boolean validatePhone(String phone){
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(phone);
		if(!m.matches()){
			return false;
		}
		return true;
	}
}
