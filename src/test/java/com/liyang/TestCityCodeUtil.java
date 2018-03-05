package com.liyang;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyang.client.tianan.CreateEnquiryJsonParseAdapterTiananImpl;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.util.CityCodeUtil;

/**
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestCityCodeUtil {

	@Test
	public void test() {
		String cityName = CityCodeUtil.getCityName("330000");
		assertEquals("浙江省", cityName);
	}

	@Test
	public void test1() {
		CreateEnquiry createEnquiry = new CreateEnquiry();
		String params = "{cityCode: 3440200}";
		createEnquiry.setCreateEnquiryParams(params);
		CreateEnquiryJsonParseAdapterTiananImpl tianan = new CreateEnquiryJsonParseAdapterTiananImpl(createEnquiry);
		String cityName = String.valueOf(tianan.getCityName());
		assertEquals("韶关市", cityName);
	}

	@Test
	public void format() throws IOException {
		JSONArray cityInfo = formatCityCode();
		File file = new File("C://cityCodeForMobile.json");
		Writer writer = new FileWriter(file);
		writer.write(cityInfo.toJSONString());
		writer.close();
	}

	/**
	 * 格式化cityCode
	 * 
	 * @return
	 */
	public JSONArray formatCityCode() {
		JSONArray cityInfo = CityCodeUtil.CITY_INFO;
		JSONArray provinceArray = new JSONArray();
		JSONArray cityArray = new JSONArray();
		JSONArray areaArray = new JSONArray();
		// 先取出所有省信息
		for (int i = 0; i < cityInfo.size(); i++) {
			JSONObject item = (JSONObject) cityInfo.get(i);
			// 省行政区划以0000结尾
			if (item.getString("code").endsWith("0000")) {
				JSONObject province = new JSONObject();
				cityArray = new JSONArray();
				province.put("city", cityArray);
				province.put("name", item.getString("name"));
				province.put("code", item.getString("code"));
				provinceArray.add(province);
			} else if (item.getString("code").endsWith("00")) {// 市行政区划以00结尾
				JSONObject city = new JSONObject();
				areaArray = new JSONArray();
				city.put("area", areaArray);
				city.put("name", item.getString("name"));
				city.put("code", item.getString("code"));
				cityArray.add(city);
			} else {// 其他都是县区
				JSONObject area = new JSONObject();
				area.put("name", item.getString("name"));
				area.put("code", item.getString("code"));
				areaArray.add(area);
			}
		}
		return provinceArray;
	}
}
