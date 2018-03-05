package com.liyang.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyang.aliyunsms.AliyunSmsException;
import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.identify.Identify;
import com.liyang.domain.identify.IdentifyRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.DisplayException;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class RegisterService {

	@Autowired
	private AliyunSmsService aliyunService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private IdentifyRepository identifyRepository;

	/**
	 * 随机6位数字生成器
	 */
	public String sixNumberGenernate() {
		Random random = new Random();
		Integer a = random.nextInt(899999) + 100000;
		return a.toString();
	}

	/**
	 * 发送验证码
	 */
	@Transactional
	public boolean sendIdentify(String phone) {
		String identify = this.sixNumberGenernate();
		if (!"18888888888".equals(phone)) {
			this.updateIdentify(phone, identify);
		}
		boolean result = false;
		try {
			result = aliyunService.sendMessage(phone, identify, "SMS_99960016");
		} catch (AliyunSmsException e) {
			e.printStackTrace();
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_ALIYUN_SEND_ERROR);
		}
		// this.updateIdentify(phone, identify);
		return result;
	}

	/**
	 * 注册逻辑 code验证码 invite邀请码
	 */
	public boolean register(String phone, String code, String... invite) {
		if (!this.validateCode(phone, code)) {
			return false;
		}
		Customer customer = new Customer();
		if (invite != null) {
			customer.setInvite(invite[0]);
		}
		customer.setCreateTime(new Date());
		customer.setGrade(1);
		customer.setPhone(phone);
		// customer.setState(0); //用户状态
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_INSERT_ERROR);
			// throw new DisplayException("数据库插入失败");
		}
		return true;
	}

	/**
	 * 把验证码放在数据库 有了就更新，没有就插入
	 */
	public void updateIdentify(String phone, String identify) {
		try {
			List<Identify> list = this.identifyRepository.getByPhone(phone);
			if (list.size() == 0) {
				Identify iden = new Identify();
				iden.setPhone(phone);
				iden.setCode(identify);
				iden.setExpire(new Date(System.currentTimeMillis() + 1000 * 60 * 60));
				this.identifyRepository.save(iden);
			} else {
				Date expire = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
				Identify obj = list.get(0);
				obj.setExpire(expire);
				obj.setCode(identify);
				this.identifyRepository.save(obj);
			}
		} catch (Exception e) {
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_SAVEIDENTI_ERROR);
			// throw new DisplayException("修改数据库发生异常");
		}
	}

	/**
	 * 验证验证码是否正确
	 */
	public boolean validateCode(String phone, String code) {
		Date expire = new Date();
		List<Identify> identifyList = identifyRepository.findByPhoneAndCodeAndExpireGreaterThan(phone, code, expire);
		if (null == identifyList || identifyList.size() == 0) {
			return false;
		}
		return true;

		/*
		 * /
		 * 
		 * Original Code for reconstru
		 */

		// List<Identify> list = this.identifyRepository.getByPhone(phone);
		// if(list.size() == 0){
		// return false;
		// }else{
		// if(!code.equals(list.get(0).getCode()) || new Date().getTime() >
		// list.get(0).getExpire().getTime()){
		// return false;
		// }
		// }
		// return true;
	}

}
