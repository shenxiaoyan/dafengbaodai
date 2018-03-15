package com.liyang.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Adam
 *
 */
public class BankCardUtil {

	/**
	 * 将bankCode.json 读取为 JSONObject
	 * 
	 * @return
	 */
	public static JSONObject readJsonToObj() {
		// 打包后获取路径错误！！！
//		String filename = "/bankCode.json";
//		String path = BankCardUtil.class.getResource(filename).getPath();
//		try {
//			String content = FileUtils.readFileToString(new File(path), "UTF-8");
//			return JSONObject.parseObject(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
		Resource resource = new ClassPathResource("bankCode.json");
		try {
			InputStream is = resource.getInputStream();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int len = 0;
			while ((len = is.read(data)) != -1) {
				os.write(data, 0, len);
			}
			String resultStr = new String(os.toByteArray(), "UTF-8");
			return JSONObject.parseObject(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 获取银行卡信息(支付宝接口)
	 * 
	 * @param cardNo
	 * @return
	 */
	public static String getCardDetail(String cardNo) {
		String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=";
		url += cardNo;
		url += "&cardBinCheck=true";
		return HttpUtil.getBody(url);
	}

	/**
	 * 银行卡号校验
	 * 
	 * @param cardNo
	 * @return
	 */
	public static boolean checkBankcard(String cardNo) {
		char jeneratedCheckCode = getBankcardCheckCode(cardNo.substring(0, cardNo.length() - 1));
		if ('N' == jeneratedCheckCode) {
			return false;
		}
		return cardNo.charAt(cardNo.length() - 1) == jeneratedCheckCode;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 该校验的过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和(含最后一位)，结果应该可以被10整除。
	 */
	public static char getBankcardCheckCode(String noCheckCodeCardNo) {
		if (null == noCheckCodeCardNo || noCheckCodeCardNo.trim().length() < 15
				|| noCheckCodeCardNo.trim().length() > 18 || !noCheckCodeCardNo.matches("\\d+")) {
			return 'N';
		}
		char[] chs = noCheckCodeCardNo.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

}
