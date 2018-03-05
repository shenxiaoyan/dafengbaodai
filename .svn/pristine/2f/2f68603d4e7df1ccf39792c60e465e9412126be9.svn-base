package com.liyang.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerActRepository;
import com.liyang.domain.customer.CustomerProjectionClass;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.customer.CustomerStateRepository;
import com.liyang.domain.identify.Identify;
import com.liyang.domain.myinvite.MyInvite;
import com.liyang.domain.myinvite.MyInviteRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.CustomerDisableException;
import com.liyang.helper.DisplayException;
import com.liyang.helper.IdentifyErrorException;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PatternUtil;

//@SessionAttributes(names="user_info")
/**
 * @author Administrator
 *
 */
@Service
public class Login2Service {
	
	@Autowired
	private CustomerRepository customerRepository; 
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private MyInviteRepository myInviteRepository ; 
	
	@Autowired
	CustomerStateRepository customerStateRepository;
	
	@Autowired
	CustomerActRepository customerActRepository;
	
	@Autowired
	InviteStringService	inviteStringService;
	
	@Autowired
	AuthorityJudgeService	authorityJudgeService;

	/**
	 * 登录逻辑
	 */
	public Customer login(String phone , String code ,HttpServletRequest request,String ...invite){
		
		// indetify phone and code
		if(!registerService.validateCode(phone,code)){
			throw new FailReturnObject(ExceptionResultEnum.LOGIN_IDENTIFY_ERROR);
//			throw new IdentifyErrorException("验证码输入错误或验证码已过期！");
		}
		
		List<Customer> cusList = customerRepository.getByPhone(phone);
		if(null==cusList || cusList.isEmpty()){
			// new customer
			Customer customer = new Customer();
			if(invite.length != 0 && null != invite[0]){
				//be invited cust
				customer.setInvite(invite[0]);
				customer.setGrade(1);
			}else {
				customer.setGrade(2);
			}
			String token=UUID.randomUUID().toString();
			//用于用户标识
			customer.setToken(token);   
			customer.setNickname(phone);
			customer.setClient(request.getHeader("client"));
			customer.setPhone(phone);
			customer.setTag(0);
			customer.setMyInvite(noRepeatSixNum());
			customer.setState(customerStateRepository.findByStateCode("ENABLED"));
			customer.setWorkflow(customerActRepository.findByActCode("create").getStartWorkflow());
			Customer reCust =customerRepository.save(customer);
			return reCust;
		}else {
			//customer exist
			Customer customer =cusList.get(0);
			// customer  authorityJudge
			authorityJudgeService.customerJudge(customer);
			
			customer.setClient(request.getHeader("client"));
			Customer reCust = customerRepository.save(customer);
			return reCust;
		}
		
		
			
		/*/
		 * Original Code for reconstr
		 * 
		 */
//		List<Customer> list = customerRepository.getByPhone(phone);
//		if(list.size() == 0){
//			//走之前的注册逻辑
//			if(!registerService.validateCode(phone,code)){
//				throw new IdentifyErrorException("验证码输入错误！");
//			}
//			Customer customer = new Customer();
//			customer.setGrade(2);
//			if(invite.length != 0 && null != invite[0]){
//				customer.setInvite(invite[0]);
//				customer.setGrade(1);
//			}
//			String token=UUID.randomUUID().toString();
//			customer.setToken(token);   //用于用户标识
//			customer.setNickname(phone);
//			customer.setClient(request.getHeader("client"));
//			customer.setPhone(phone);
//			customer.setTag(0);
//			customer.setMyInvite(noRepeatSixNum());
//			customer.setState(customerStateRepository.findByStateCode("ENABLED"));
//			customer.setWorkflow(customerActRepository.findByActCode("create").getStartWorkflow());
//			try{
//				customerRepository.save(customer);
//			}catch(Exception e){
//				throw new DisplayException("数据库插入失败");
//			}
//			return true;
//		}else{
//			List<Customer> list2 = customerRepository.getByPhone(phone);
//			Customer c = null;
//			if(null != list2) {
//				c = list2.get(0);
//			}
//			//用户被禁用
//			if("DISABLED".equals(c.getState().getStateCode())){
//				throw new CustomerDisableException();
//			}
//			c.setClient(request.getHeader("client"));
//			customerRepository.save(c);
//			if(!registerService.validateCode(phone, code)){
//				throw new IdentifyErrorException("验证码输入错误！");
//			}
//			return true;
//		}
	}
	
	/**
	 * 生成不重复的随机6位数字
	 */
	@Transactional
	public String noRepeatSixNum(){
		List<Customer> cusList=null;
		StringBuffer randomStrBuf=null;
		do {
			randomStrBuf= inviteStringService.randomString();
			if(null==randomStrBuf) {
				throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_INI_INVITE_ERROR);
			}
			 cusList =customerRepository.findByMyInvite(randomStrBuf.toString());
			
		}while(null!=cusList && !cusList.isEmpty());
		
		return randomStrBuf.toString();
		

		/*
		 * orginal code 4 reconstru
		 * 
		 */
//		Pageable page = new PageRequest(1,1);
//		Page<MyInvite> page1 = myInviteRepository.getOneMyInvite( 0 , page);
//		MyInvite invite = page1.getContent().get(0);
//		System.out.println(invite.getId());
//		invite.setTag(1);
//		return invite.getRandomString();
	}

	public ResponseBody loginIn4Mob(Identify iden, HttpSession session, HttpServletRequest request) {
		String phone = iden.getPhone();
		String code = iden.getCode();
		String invite = null ;
		
		if(null != iden.getInvite()){
			invite = iden.getInvite();
		}
		if(!PatternUtil.validatePhone(phone)){
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_CELLFORMART_ERROR);
//			return new ResponseBody(100,"手机号码格式不正确");
		}		
		Customer customer =null;
		if((customer=this.login(phone, code,request,invite))!= null){
//			 customer = customerRepository.getByPhone(phone).get(0);
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Customer.class, "id","client","grade","headimgurl","invite",
					"myInvite","nickname","phone","sex","tag","token","account");  
			String custJsonStr = JSON.toJSONString(customer, filter);
			JSONObject custJsonObj=JSON.parseObject(custJsonStr);
//			ResponseBody response = new ResponseBody(0,"ok");
//			CustomerProjectionClass cusProClass=dataPackage(customer);  //数据封装
//			response.setData(custJsonObj);
			return new ResponseBody(custJsonObj);			

		}else{
			throw new FailReturnObject(ExceptionResultEnum.LOGIN_IDENTIFY_ERROR);
//			return new ResponseBody(100,"登录失败");
		}

		
	}
	
//	public CustomerProjectionClass dataPackage(Customer customer) {
//		CustomerProjectionClass customerProjectionClass=new CustomerProjectionClass();
//		customerProjectionClass.setId(customer.getId());
//		customerProjectionClass.setClient(customer.getClient());
//		customerProjectionClass.setCreateTime(customer.getCreateTime());
//		customerProjectionClass.setGrade(customer.getGrade());
//		customerProjectionClass.setHeadimgurl(customer.getHeadimgurl());
//		customerProjectionClass.setInvite(customer.getInvite());
//		customerProjectionClass.setMyInvite(customer.getMyInvite());
//		customerProjectionClass.setNickname(customer.getNickname());
//		customerProjectionClass.setPhone(customer.getPhone());
//		customerProjectionClass.setPushToken(customer.getPushToken());
//		customerProjectionClass.setSex(customer.getSex());
//		customerProjectionClass.setTag(customer.getTag());
//		customerProjectionClass.setToken(customer.getToken());
//		customerProjectionClass.setUpdateTime(customer.getUpdateTime());
//		customerProjectionClass.setAccount(customer.getAccount());
//		return customerProjectionClass;
//	}
}
