package com.liyang.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.liyang.aliyunsms.AliyunSmsException;
import com.liyang.aliyunsms.AliyunSmsService;
import com.liyang.domain.account.Account;
import com.liyang.domain.account.AccountRepository;
import com.liyang.domain.account.AccountVO;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.upgradeapply.UpgradeApply;
import com.liyang.domain.upgradeapply.UpgradeApplyRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.util.BankCardUtil;
import com.liyang.util.CodeUtil;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class AccountService {

	@Autowired
	private UpgradeApplyRepository upgradeApplyRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	AliyunSmsService smsService;

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	public List<UpgradeApply> queryUpgrade() {
		return upgradeApplyRepository.query();
	}

	@Transactional
	public void accpetUpgrade(UpgradeApply apply) {
		List<Customer> list = customerRepository.getByPhone(apply.getPhone());
		if (list.size() == 0) {
			throw new FailReturnObject(ExceptionResultEnum.ADMIN_NOUSERFOUND_ERROR);
		}
		if (list.get(0).getTag() == 1) {
			throw new FailReturnObject(ExceptionResultEnum.ADMIN_ACCPETUPGRADE_ERROR);
		}
		changeApplyState(apply);
		Account account = saveAccount(apply);
		changeUserState(apply, account);
	}

	/**
	 * 修改认证后的申请状态
	 * 
	 * @param apply
	 */
	public void changeApplyState(UpgradeApply apply) {
		UpgradeApply applyReal = upgradeApplyRepository.queryById(apply.getId());
		applyReal.setAuditTime(new Date());
		applyReal.setStatus(1);
		upgradeApplyRepository.save(applyReal);
	}

	/**
	 * 修改实名认证后的粉丝状态
	 * 
	 * @param apply
	 * @param account
	 */
	private void changeUserState(UpgradeApply apply, Account account) {
		List<Customer> list = customerRepository.getByPhone(apply.getPhone());
		if (list.size() == 0) {
			throw new FailReturnObject(ExceptionResultEnum.ADMIN_NOUSERFOUND_ERROR);
		}
		list.get(0).setAccount(account);
		list.get(0).setTag(1);
	}

	/**
	 * 新建实名认证后的账户
	 * 
	 * @param apply
	 * @return
	 */
	private Account saveAccount(UpgradeApply apply) {
		Account account = new Account();
		account.setBalance(0);
		account.setCreateTime(new Date());
		account.setIdentity(apply.getIdentify());
		account.setPayAccountId(apply.getPayAccountId());
		account.setPayAccountPlatform(apply.getPayAccountPlatform());
		account.setPhone(apply.getPhone());
		account.setRealName(apply.getRealName());
		account.setStatus(0);
		account.setTotalBalance(0);
		accountRepository.save(account);
		return account;
	}

	// ------------------------- Mobile Bankcard Methods----------------------------

	/**
	 * 生成/更新 并发送短信验证码
	 * 
	 * @param header
	 */
	public void sendVerificationCode(String token) {
		Customer customer = customerRepository.findByToken(token);
		if (0 == customer.getTag() || null == customer.getAccount()) {
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_NOACCOUNT_ERROR);
		}
		String verificationCode = CodeUtil.generateVerificationCode();
		customer.getAccount().setVerificationCode(verificationCode);
		customer.getAccount().setCodeExpireTime(new Date(System.currentTimeMillis() + 1000 * 60 * 15));
		accountRepository.save(customer.getAccount());
		try {
			smsService.sendMessage(customer.getPhone(), verificationCode, "SMS_99960016");
		} catch (AliyunSmsException e) {
			logger.error("短信验证码发送错误！");
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_ALIYUN_SEND_ERROR);
		}
	}

	/**
	 * 根据银行卡号获取发卡行、图标(支付宝接口)
	 * 
	 * @param cardNo
	 */
	public Map<String, String> getBankCardDetail(String cardNo) {
		if (!BankCardUtil.checkBankcard(cardNo)) {
			throw new FailReturnObject(ExceptionResultEnum.BANKCARDNO_ERROR_LUHM);
		}
		String cardDetail = BankCardUtil.getCardDetail(cardNo);
		JSONObject jsonObject = JSONObject.parseObject(cardDetail);
		if (!jsonObject.getBoolean("validated")) {
			throw new FailReturnObject(ExceptionResultEnum.BANKCARDNO_DISTINGUISH_ERROR_ALIPAY);
		}
		Map<String, String> resultMap = new HashMap<>();
		String bankCode = jsonObject.getString("bank");
		resultMap.put("bankName", BankCardUtil.readJsonToObj().getString(bankCode));
		resultMap.put("bankIcon", "https://apimg.alipay.com/combo.png?d=cashier&t=" + bankCode);
		return resultMap;
	}

	/**
	 * 根据token，添加银行卡
	 * 
	 * @param token
	 * @param cardNo
	 */
	// TODO 增加单位时间内，限制请求次数
	@Transactional
	public void addBankCardByToken(String token, AccountVO accountVO) {
		Customer customer = customerRepository.findByToken(token);
		checkQualification(customer, accountVO.getVerificationCode());
		// 目前流程是直接修改，不使用解绑功能，暂注释
		// if (null == customer.getAccount().getBankcardBinded() ||
		// customer.getAccount().getBankcardBinded()) {
		// throw new FailReturnObject(ExceptionResultEnum.BANKCARD_BINDED_ERROR);
		// }
		if (!BankCardUtil.checkBankcard(accountVO.getBankcardNo())) {
			throw new FailReturnObject(ExceptionResultEnum.BANKCARDNO_ERROR_LUHM);
		}
		BeanUtils.copyProperties(accountVO, customer.getAccount(), CommonUtil.getNullPropertyNames(accountVO));
		customer.getAccount().setBankcardBinded(true);
		customerRepository.save(customer);
	}

	/**
	 * 根据token，解绑银行卡
	 * 
	 * @param header
	 */
	@Transactional
	public void unbindBankcardByToken(String token, String verificationCode) {
		Customer customer = customerRepository.findByToken(token);
		checkQualification(customer, verificationCode);
		if (!customer.getAccount().getBankcardBinded()) {
			throw new FailReturnObject(ExceptionResultEnum.BANKCARD_UNBINDED_ERROR);
		}
		customer.getAccount().setBankcardBinded(false);
		accountRepository.save(customer.getAccount());
	}

	/**
	 * 进行银行卡操作前相关验证
	 * 
	 * @param customer
	 * @param verificationCode
	 */
	public static void checkQualification(Customer customer, String verificationCode) {
		if (0 == customer.getTag() || null == customer.getAccount()) {
			throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_NOACCOUNT_ERROR);
		}
		if (StringUtils.isEmpty(verificationCode) || StringUtils.isEmpty(customer.getAccount().getVerificationCode())
				|| !verificationCode.equals(customer.getAccount().getVerificationCode())) {
			throw new FailReturnObject(ExceptionResultEnum.BANKCARD_VERIFICATIONCODE_ERROR);
		}
		if (customer.getAccount().getCodeExpireTime().before(new Date())) {
			throw new FailReturnObject(ExceptionResultEnum.BANKCARD_VERIFICATIONCODE_EXPIRE_ERROR);
		}
	}
	

}
