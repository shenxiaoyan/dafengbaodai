package com.liyang.baidu;

import java.util.HashMap;

import org.json.JSONObject;
import org.junit.Test;

import com.baidu.aip.ocr.AipOcr;

public class OcrLicenseDemo {
	
	public static final String APP_ID = "10861977";
	public static final String API_KEY = "svNigGvjXu6U9OeXn7V9QHCr";
	public static final String SECRET_KEY = "xfM6uTxMxsrIwmXCyVQ1kMLO4k5Qo38Q";
	
	@Test
	public void testSample() {
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		HashMap<String, String> options = new HashMap<>();
		options.put("detect_direction", "true");
		options.put("accuracy", "normal");
		String path = getClass().getResource("").getFile().toString();
		String image = "4.jpg";
		JSONObject object = client.vehicleLicense(path + image, options);
		System.out.println(object.toString(2));
	}
	
	
	
	
	
}
