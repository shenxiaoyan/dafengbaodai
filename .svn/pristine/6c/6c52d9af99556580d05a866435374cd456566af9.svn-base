package com.liyang.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RegexTest {
	
	@Test
	public void testChinese() {
		String str = "麤发了收到积分";
		String regex = "[\\u4e00-\\u9fa5]*";
		
		if (str.matches(regex)) {
			System.out.println("是中文");
			System.out.println(str.length());
		}else {
			System.out.println("不是");
		}
	}
	
	@Test
	public void testIdReg() {
		String regex = "^\\d{15}$|^\\d{17}[0-9Xx]$";
		String str1 = "32098119901010479x";
		String str2 = "32098119901010479X";
		if (str1.matches(regex)) {
			System.out.println("st1符合");
		}
		if (str2.matches(regex)) {
			System.out.println("str2符合");
		}
	}
	
	
	
	
	
	
	
	@Test
	public void testPattern() {
		String regex = "1|2|3";
		String i = "1";
		if (i.matches(regex)) {
			System.out.println("1 匹配");
		}
		String j = "4";
		if (j.matches(regex)) {
			System.out.println("4pi");
		}
	}
	
	
	
	
	@Test
	public void testDeliver() {
		Map<String, String> map = new HashMap<>();
		map.put("original", "原来的");
		put(map);
		System.out.println(map);
		
	}
	
	public Map<String, String> put(Map<String, String> map){
		map.put("test", "方法中加的");
		return map;
	}
	
	@Test
	public void testBigDecimal() {
		double d = 2.0;
		BigDecimal b= new BigDecimal(d);
		d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(d);
		System.out.println(b);
	}
	
	@Test
	public void testDecimalFormat() {
		double d = 2.0;
		DecimalFormat df = new DecimalFormat("#0.00");
		String format = df.format(d);
		System.out.println(format);
	}
	
	
}
