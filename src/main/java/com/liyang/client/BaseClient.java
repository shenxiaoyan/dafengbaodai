package com.liyang.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.util.HttpUtil;
/**
 * 
 * @author Administrator
 *
 */
public class BaseClient implements IClient {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseClient.class);

	protected String baseUrl;
	
	public BaseClient(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the baseUrl
	 */
	@Override
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl
	 *            the baseUrl to set
	 */
	@Override
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object postData(String url, Object params) {
		String result = "";
		Map<String, String> formParams = (Map<String, String>) params;
		if (formParams == null) {
			return result;
		}
		System.out.println("【BaseClient postData url】:" + url);
		System.out.println("【BaseClient postData formParams】："+formParams);
		result = HttpUtil.postForm(url, formParams);
		System.out.println("【BaseClient postData originalResult】："+result);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getData(String url, Object params) {
		String result = "";
		//TODO   如果param不是map，不需加判断？？？
		Map<String, String> formParams = (Map<String, String>) params;
		if (formParams == null) {
			return result;
		}
		String finalUrl = url + "?";
		if (formParams != null && formParams.size() > 0) {
			for (Map.Entry<String, String> entry : formParams.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (finalUrl.endsWith("?")) {
					finalUrl += key + "=" + value;
				} else {
					finalUrl += "&" + key + "=" + value;
				}
			}
		}
		System.out.println("url:" + url);
		result = HttpUtil.getBody(finalUrl);

		return result;
	}

	@Override
	public Object postData(String url, Object params, Object headers) {
		// todo 暂时没有支持 header的实现
		return postData(url, params);
	}

	@Override
	public Object getData(String url, Object params, Object headers) {
		// todo 暂时没有支持header的实现
		return getData(url, params);
	}

	@Override
	public Object postJson(String url, String jsonString) {
		Object result = null;
		System.out.println("【BaseClient postJson url】:" + url);
		System.out.println("【BaseClient postJson jsonString】:" + jsonString);
		String charset = "UTF-8";
		try {
			// 创建连接
			URL urlobj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlobj.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// connection.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			//connection.setRequestProperty("Content-Encoding", charset);
			connection.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			// out.writeBytes(jsonString);
			// 修改中文乱码问题
			out.write(jsonString.getBytes(charset));
			out.flush();
			out.close();

			// 读取响应
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(connection.getInputStream()));
			//设置指定接受字符集，解决乱码问题
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				System.out.println("----------------------------------------");
				System.out.println("response original line:" + lines);
				System.out.println("----------------------------------------");
				// lines = new String(lines.getBytes(), charset);
				sb.append(lines);
			}
			//System.out.println("----------------------------------------");
			//System.out.println("response:" + sb.toString());
			//System.out.println("----------------------------------------");
			result = sb.toString();
			reader.close();
			// 断开连接
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("postJson无法获取到连接,请检查url");
		}
		logger.info("【BaseClient postJson originalResult】："+result);
		return result;
	}

	@Override
	public Object postJson(String url, String jsonString, Object headers) {
		return postJson(url, jsonString);
	}

	@Override
	public Object postFile(String url, Object params, Object headers) throws IOException {
		MultipartFile file = (MultipartFile) params;
		return HttpUtil.postFile(file, url);
	}

	@Override
	public Object postFile(String url, Object params) throws Exception {
		return postFile(url, params, null);
	}
}
