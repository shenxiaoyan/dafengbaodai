package com.liyang.aliyunsms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */

@Service
public class AliyunSmsService {
	@Value("${spring.aliyun.accessKey}")
	private String accessKey;
	
	@Value("${spring.aliyun.accessSecret}")
	private String accessSecret;
	
	@Value("${spring.aliyun.signName}")
	private String signName;
	
	/**
	 * 发送短信验证码，phone是手机号,template是短信模板,code是验证码的内容
	 * 这里还是把template抽象出来了 我们的   注册模板号是SMS_86715171
	 * 登录模板号是 SMS_86660176
	 */
	public boolean sendMessage(String phone ,String code, String template ) throws AliyunSmsException{
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		//短信API产品名称（短信产品名固定，无需修改）
		final String product = "Dysmsapi";
		//短信API产品域名（接口地址固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";
		//替换成你的AK
		//你的accessKeyId,参考本文档步骤2
		final String accessKeyId = accessKey;
		//你的accessKeySecret，参考本文档步骤2
		final String accessKeySecret = accessSecret;
		//初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		try{
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		}catch(Exception e){
			throw new AliyunSmsException(e.getMessage());
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phone);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(template);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam("{\"product\":\"大蜂保代\", \"code\":\""+code+"\"}");
		//可选-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		try{
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				return true;
			}
			else{
				throw new AliyunSmsException(sendSmsResponse.getCode());
			}
		}catch(Exception e){
			throw new AliyunSmsException(e.getMessage());
		}
	}

	/**
	 * 发送短信通知，phone是手机号,template是短信模板,code是通知的内容
	 * 这里还是把template抽象出来了 我们的   注册模板号是SMS_86715171
	 * 登录模板号是 SMS_86660176
	 */

	public boolean sendMes(String phone, Map<String, Object> info, String template) {
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId = accessKey;//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = accessSecret;//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
				accessKeySecret);
		try{
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		}catch(Exception e){
			throw new AliyunSmsException(e.getMessage());
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phone);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(template);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		if (!StringUtils.isEmpty(info.get("cio"))&&StringUtils.isEmpty(info.get("bio"))){
			request.setTemplateParam("{\"product\":\"大蜂保险\", \"insuredName\":\""+info.get("insuredName")+"\" , \"license\":\""+info.get("license")+"\", \"account\":\""+info.get("account")+"\",  \"insureCompany\":\""+info.get("insureCompany")+"\" , \"bio\":\"未投保\",\"cio\":\""+info.get("cio")+"\"}");
		}else if (StringUtils.isEmpty(info.get("cio"))&&!StringUtils.isEmpty(info.get("bio"))){
			request.setTemplateParam("{\"product\":\"大蜂保险\", \"insuredName\":\""+info.get("insuredName")+"\" , \"license\":\""+info.get("license")+"\", \"account\":\""+info.get("account")+"\",  \"insureCompany\":\""+info.get("insureCompany")+"\" , \"bio\":\""+info.get("bio")+"\",\"cio\":\"未投保\"}");
		}else {
			//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			request.setTemplateParam("{\"product\":\"大蜂保险\", \"insuredName\":\"" + info.get("insuredName") + "\" , \"license\":\"" + info.get("license") + "\", \"account\":\"" + info.get("account") + "\",  \"insureCompany\":\"" + info.get("insureCompany") + "\" , \"bio\":\"" + info.get("bio") + "\",\"cio\":\"" + info.get("cio") + "\"}");
		}
		//可选-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		try{
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				return true;
			}
			else{
				throw new AliyunSmsException(sendSmsResponse.getCode());
			}
		}catch(Exception e){
			throw new AliyunSmsException(e.getMessage());
		}
	}
}
