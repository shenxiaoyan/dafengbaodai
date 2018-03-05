/**
 * 项目名称: apiDemo <br/>
 * 文件名称: HttpUtil.java <br/>
 * 包名称  : api.demo <br/>
 * 创建时间: 2017年3月15日 <br/>
 * 创建者  : IDATAG009 <br/>
 * Copyright © 2017 无锡智道安盈科技有限公司 All Rights Reserved. <br/>
 *
 */
package com.liyang.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Administrator
 *
 */
public class HttpUtil {
	/**
	 * 相关配置
	 */
	public static final CloseableHttpClient CLOSEABLEHTTPCLIENT;
	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	private static final int CONNECT_TIME_OUT = 5000 * 2;
	// 会话时间2分钟
	private static final int SOCKET_TIME_OUT = 2 * 60 * 1000;
	private static final Integer RESPONSE_OK = 200;
	private static final Integer RESPONSE_PATCH_OK = 204;
	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT)
				.setSocketTimeout(SOCKET_TIME_OUT).build();
		CLOSEABLEHTTPCLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
    
    public static String postForm(String url,Map<String,String> formParams){
        try { 
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for(Entry<String,String> entry : formParams.entrySet()){
                params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = CLOSEABLEHTTPCLIENT.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() == RESPONSE_OK){
                return EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
            // 请求失败
            System.out.println("【HttpUtil postForm请求失败】！！！ responsePhrase:"+httpResponse.getStatusLine().getReasonPhrase()
            		+"statusCode"+httpResponse.getStatusLine().getStatusCode());
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   
    
    public static String postFile(MultipartFile file,String url) throws IOException {
        String result = "";
        try {
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName); 
            builder.addTextBody("filename", fileName);
            HttpEntity entity=builder.build();
            httpPost.setEntity(entity);
            // 执行提交
            HttpResponse response = CLOSEABLEHTTPCLIENT.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String patchBody(String url,String body){
        try {
            HttpPatch httpPatch = new HttpPatch(url);
            httpPatch.setEntity(EntityBuilder.create().setText(body).build());
            httpPatch.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            HttpResponse httpResponse = CLOSEABLEHTTPCLIENT.execute(httpPatch);
            if(httpResponse.getStatusLine().getStatusCode()==RESPONSE_PATCH_OK){
                return "SUCCESS";
            }
            // 请求失败
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    public static String postBody(String url,String body){
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(EntityBuilder.create().setText(body).setContentEncoding(CHARSET_UTF8).build());
            HttpResponse httpResponse = CLOSEABLEHTTPCLIENT.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() == RESPONSE_OK){
                return EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
            // 请求失败
            System.out.println("【HttpUtil postBody请求失败】！！！ responsePhrase:"+httpResponse.getStatusLine().getReasonPhrase()
            		+"statusCode"+httpResponse.getStatusLine().getStatusCode());
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String postRawJsonBody(String url,String body){
        try {
            HttpPost httpPost = new HttpPost(url);
            HttpEntity entity =EntityBuilder.create().setText(body).setContentEncoding(CHARSET_UTF8).setContentType(
            		ContentType.APPLICATION_JSON).build();
            httpPost.setEntity(entity);
            HttpResponse httpResponse = CLOSEABLEHTTPCLIENT.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() == RESPONSE_OK){
                return EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
            // 请求失败
            System.out.println("【HttpUtil postRawJsonBody请求失败】！！！ responsePhrase:"+httpResponse.getStatusLine().getReasonPhrase()
            		+"statusCode"+httpResponse.getStatusLine().getStatusCode());
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static String getBody(String url) {
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = CLOSEABLEHTTPCLIENT.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == RESPONSE_OK) {
				return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			}
			// 请求失败
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
 
}
