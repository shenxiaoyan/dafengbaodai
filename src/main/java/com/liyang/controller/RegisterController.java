package com.liyang.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.user.UserRepository;
import com.liyang.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.RegisterService;
import com.liyang.util.FailReturnObject;


/**
 * 验证手机，获取验证码等
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value="/dafeng")
public class RegisterController{
	
	@Autowired
	private RegisterService service; 
	
	@Autowired
	CustomerRepository customerRepository;
 	@Autowired
	SmsService smsService;
	/*
	 * 不需要注册，所以被废弃了
	 */

//	//@RequestMapping("/register")
//	public ResponseBody register(@RequestParam(value="phone") String phone,
//								@RequestParam(value="code") String code, 
//								@RequestParam(value="invite",required=false) String invite){
//		if(!(this.validatePhone(phone).getErrorCode().equals(0))){
//			return this.validatePhone(phone);
//		}
//		boolean result = service.register(phone, code, invite);
//		if(result){
//			return new ResponseBody(0,"ok");
//		}else{
//			return new ResponseBody(100,"验证码错误");
//		}
//	}

	
	
	/**
	 * TODO 防止短信刷取攻击
	 * 点击获取验证码时的接口
	 * 手动数据验证待完善 
	 */
	@RequestMapping("/getIdentify")
	public ResponseBody getIdentify(@RequestParam(value="phone") String phone){
		
		this.validatePhone2(phone);
		boolean result = service.sendIdentify(phone);
		if(result){
			return new ResponseBody(ExceptionResultEnum.SUCCESS);
		}else{
			return new ResponseBody(ExceptionResultEnum.REGIS_MOB_ALIYUN_RETURN_ERROR);
		}
		
		
		
	/*
	 * Original code for recostru
	 * /	
	 */
//		if(!(this.validatePhone2(phone).getErrorCode().equals(0))){
//			return this.validatePhone2(phone);
//		}
//		
//		boolean result = service.sendIdentify(phone);
//		if(result){
//			return new ResponseBody(0,"ok");
//		}else{
//			return new ResponseBody(100,"发送验证码失败");
//		}
	}
	
	@RequestMapping("/getIdentifyWhenInvite")
	public ResponseBody getIdentifyWhenInvite(@RequestParam(value="phone") String phone){
		
		this.validatePhone2(phone);
		List<Customer> c = customerRepository.getByPhone(phone);
		if(c.size() != 0){
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_INVI_CUSEXSIST_ERROR);
		}
		boolean result = service.sendIdentify(phone);
		if(result){
			return new ResponseBody(ExceptionResultEnum.SUCCESS);
		}else{
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_ALIYUN_RETURN_ERROR);
		}
		/*
		 * Original code for recostru
		 * /	
		 */
//		if(!(this.validatePhone2(phone).getErrorCode().equals(0))){
//			return this.validatePhone2(phone);
//		}
//		List<Customer> c = customerRepository.getByPhone(phone);
//		if(c.size() != 0){
//			return new ResponseBody(100,"您已注册！");
//		}
//		boolean result = service.sendIdentify(phone);
//		if(result){
//			return new ResponseBody(0,"ok");
//		}else{
//			return new ResponseBody(100,"发送验证码失败");
//		}
	}
	
	
	/**
	 * 验证手机号码手写
	 */
	public ResponseBody validatePhone(String phone){
		List<Customer> list = (List<Customer>)customerRepository.getByPhone(phone);
		if(list.size() != 0){
			return new ResponseBody(100,"该手机号码已注册");
		}
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(phone);
		if(!m.matches()){
			return new ResponseBody(100,"请输入正确的手机号码");
		}
		return new ResponseBody(0,"ok");
	}
	
	/**
	 * 验证手机号码手写
	 */
	public Boolean validatePhone2(String phone){
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(phone);
		if(!m.matches()){
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_CELLFORMART_ERROR);
//			return new ResponseBody(100,"请输入正确的手机号码");
		}
		return true;
//		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}
//	@PostMapping("/sendMes")
// public  ResponseBody sendMes(@RequestBody Map<String,Object> requestParam){
//		Boolean send = smsService.sendMessage((String) requestParam.get("insuredName"), (String) requestParam.get("license"), (BigDecimal) requestParam.get("account"), (Integer) requestParam.get("customerId"));
//		if (send==false){
//			throw  new FailReturnObject(777,"信息发送失败，请注意查看!");
//		}
//		return  new ResponseBody("发送成功");
//	}
}
