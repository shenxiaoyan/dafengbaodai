package com.liyang;

import java.util.Date;

import org.junit.Test;

import com.liyang.helper.IDCardFormatException;
import com.liyang.util.IDCardUtil;

/**
 * @author Administrator
 *
 */
public class TestIDCardUtil {

	@Test
	public void test() throws IDCardFormatException  {
//		IDCardUtil util = new IDCardUtil();
		String idCard = "36242119970925172X";
//		boolean isTrue = IDCardUtil.IDVallidate(IdCard);
//		System.out.println(isTrue);
		
		Boolean sex = IDCardUtil.isMale(idCard);
		System.out.println(sex);
		
		Date date = IDCardUtil.getBirthByIdCard(idCard);
		System.out.println(IDCardUtil.getBirthByIdCard(idCard));
		
		
	}

}
