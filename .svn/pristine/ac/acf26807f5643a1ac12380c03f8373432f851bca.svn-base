package com.liyang.test;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.util.PatternUtil;

/**
 * @author Administrator
 *
 */
public class PhoneTest {

	@Test
	public void test() {
		String telePhone = "15984756234";
		boolean flag = false;
		Pattern p1 = PatternUtil.MOBILEPHONE_PATTERN;
		Pattern p2 = PatternUtil.TELEPHONE_PATTERN;
		if (telePhone.length() == 11 && p1.matcher(telePhone).matches()) {
			flag = true;
		} else {
			System.out.println("收件人手机号码格式不正确");
		}
		if (telePhone.length() < 16 && p2.matcher(telePhone).matches()) {
			flag = true;
		} else {
			System.out.println("收件人固定电话格式不正确，应为XXXX-XXXXXXXX");
		}
		if (flag) {
			System.out.println("geshizhengque");
		} else {
			System.out.println("error");
		}
	}

}
